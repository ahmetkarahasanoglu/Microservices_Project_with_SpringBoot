package com.ahmet.utility;

import com.ahmet.repository.entity.BaseEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public class ServiceManager<T extends BaseEntity,ID> implements IService<T,ID> {
                             // '--> T'yi, BaseEntity'den miras aldırdık. (Artık aşağıdaki metotlarda t.setCreateat() gibi metotlar kullanabileceğiz).

    private final ElasticsearchRepository<T,ID> repository; // Burda ElasticsearchRepository kullanıyoruz. Diğer JpaRepository ise ilişkisel veri tabanı içindir, onu kullanmıyoz burda.

    public ServiceManager(ElasticsearchRepository<T,ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        long time = System.currentTimeMillis();
        t.setCreateat(time);
        t.setUpdateat(time);
        t.setState(true);           // ekledik buraları.
        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        t.forEach(x-> {
            long time = System.currentTimeMillis();
            x.setCreateat(time);
            x.setUpdateat(time);
            x.setState(true);           // ekledik buraları.
        });
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdateat(System.currentTimeMillis());  // ekledik burayı.
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<T> findAll() { // Burada normalde List<T> yazıyordu, ElasticsearchRepository 'Iterable' kullandığı için 'Iterable'a çevirdik burayı.
        return repository.findAll(); // '--> Bir de IService'deki findAll'u da Iterable yaptık.
    }
}
