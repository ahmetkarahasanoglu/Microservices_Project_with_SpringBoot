package com.ahmet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequestDto {

    String token;
    Integer pageSize; // Her bir istekte göndermek istediğimiz kayıt adedi
    Integer currentPage; // Şu an bulunduğumuz, talep ettiğimiz sayfa numarası
    String sortParameter; // Hangi alana göre sıralama yapılacak ise o alanın adı
    String direction; // Sıralamanın yönü; a..Z, ASC, DESC

}
