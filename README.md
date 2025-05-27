# ⚡ Enerji Dağıtım ve Arıza Müdahale Otomasyonu

## 📘 Proje Özeti
Bu proje, İstanbul’daki elektrik dağıtım ağını simüle ederek, ilçelerdeki elektrik durumunu izlemeyi ve harita üzerinden arızaları tespit edip müdahale etmeyi amaçlar.

---

## 👨‍💻 Proje Üyeleri
- Muhammet Emin Balmuk  
- Ertuğrul Selim Öztürk  
- Çağatay Sofu  
- Zeynep Yavuz

---

## 🎯 Projenin Amacı
Kullanıcının, harita üzerinden arızalı bölgeleri görebilmesini ve tıklayarak sistemin o bölgeye müdahale etmesini sağlamaktır.

---

## 🛠 Kullanılan Teknolojiler
- Java (Spring Boot)
- HTML + JavaScript (Leaflet.js ile harita gösterimi)
- Veri yapıları: Stack, Queue, HashMap, Binary Tree

---

## 🏗️ Proje Mimarisi

**MVC Yapısı:**
- **Model**: `BolgeIlce`, `EnerjiPaketi`, `ArizaMudahale`, `RegionTree`, `Stack`, `Queue`, `Node`
- **View**: HTML (Leaflet ile harita)
- **Controller**: `DistrictController`

---

## 📈 UML Diyagramı
UML diyagramı, sınıflar ve veri yapıları arasındaki ilişkileri gösterir.  
![UML diyagram resim](https://github.com/user-attachments/assets/31f1b528-d0fd-4a14-beb2-e3a9862ce5df)


---

## 🧑‍💻 USE-CASE Diyagramı

| Aktör | Fonksiyonlar |
|-------|--------------|
| Sistem | Arıza Üret, Enerji Transferini Yönet, Rastgele Arıza Ekle |
| Kullanıcı | İlçe Bilgilerini Görüntüle, Arıza Düzelt, Bölge Ağaç Görüntüle |

---

## 🚦 Senaryo

1. Sistem başlatılır, enerji merkezlerinden enerji üretimi başlar.
2. Stack yapılarında enerji paketleri birikir.
3. Enerjiler, sırayla Queue yapısına aktarılır ve ardından ağaç yapısıyla ilçelere iletilir.
4. %30 ihtimalle hatalı enerji paketleri oluşur.
5. Her 4 saniyede sistem rastgele yeni bir arıza üretir.
6. Kullanıcı, harita arayüzünden ilçelere tıklayarak arızaları tespit ve müdahale eder.

---

## 🖼️ Arayüz
Harita üzerinden ilçeler gözlemlenir:  
- **Kırmızı çizgi**: Hatalı iletim  
- **Yeşil çizgi**: Sağlıklı iletim
<img width="934" alt="image" src="https://github.com/user-attachments/assets/41192cb7-3714-4c91-b029-bfb1998c36ca" />
<img width="935" alt="image" src="https://github.com/user-attachments/assets/26104ccb-2692-416f-967e-11b0948a92bd" />

---

## 🧾 Proje Dosya Açıklamaları

### `DistrictController.java`
Elektrik arızalarını yönetir, ilçelere ait durumları kontrol eder.

### `ArizaMudahale.java`
İlçelerdeki arızaları Hash tabanlı veri yapısı ile saklar.

### `BolgeIlce.java`
Bir ilçeyi ve ait olduğu bölgeyi temsil eden veri modeli.

### `EnerjiPaketi.java`
Her bir enerji paketinin `id` bilgisini tutar.

### `Main.java`
Sistemin başlangıç noktası; enerji üretimi, dağıtımı ve arıza simülasyonlarını yönetir.

### `Node.java`
Hem ağaç hem de bağlı liste elemanı olarak kullanılan düğüm sınıfı.

### `Queue.java`
Bağlı liste temelli kuyruk veri yapısı.

### `RegionTree.java`
"İstanbul > Merkez > İlçeler" şeklinde bölgesel ağaç yapısını kurar.

### `Stack.java`
Enerji paketlerini geçici olarak tutar, ardından Queue'ya aktarır.

### `Uygulama.java`
Spring Boot uygulamasının başlatıcı sınıfıdır.

---

## 🚀 Başlatmak için

```bash
cd proje
mvn spring-boot:run
