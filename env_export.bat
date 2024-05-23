@echo off
setx JAVA_SECRETKEY "12345"  @REM Sistem Ortam Değişkenlerine buradan da kaydedebiliyoruz bu şekilde.
setx JAVA6_ISSUER "Java6"

@REM Terminal'den bu dosyayı çalıştırmak için Terminal'e şöyle yazıyoruz: .\env_export.bat (terminalde root konumundayken) / (ya da bu .bat dosyasına çift tıklayınca da oluyomuş aynı şey)