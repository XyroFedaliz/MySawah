# MySawah - Smart Farming Assistant & Marketplace 🌾

**MySawah** adalah aplikasi Android modern yang dirancang untuk membantu petani meningkatkan produktivitas melalui integrasi teknologi Kecerdasan Buatan (AI) dan E-commerce pertanian. Aplikasi ini dibangun menggunakan **Jetpack Compose** dengan arsitektur yang bersih dan reaktif.

---

## 🚀 Fitur Unggulan

### 🤖 AI Crop Recommendation
Sistem cerdas yang merekomendasikan tanaman terbaik untuk lahan Anda.
* Menggunakan model **Random Forest Classifier**.
* Menganalisis 7 parameter tanah: Nitrogen (N), Fosfor (P), Kalium (K), Suhu, Kelembaban, pH, dan Curah Hujan.
* Interface berbasis chat yang interaktif.

### 🛒 Smart Marketplace & Cart
* **Real-time Sync:** Keranjang belanja tersinkronisasi langsung dengan database server (Laravel).
* **Pending Order System:** Barang yang dimasukkan ke keranjang tersimpan aman meskipun aplikasi ditutup.
* **Stock Validation:** Pengurangan stok otomatis saat transaksi berhasil (Status PAID).

### 💳 Transaction History
* Riwayat pesanan mendetail untuk memantau pengeluaran dan kebutuhan tani.
* Status transaksi transparan (Pending/Paid).
* Format mata uang Rupiah otomatis.

### 👤 Profile Management
* **Update Profile:** Ubah Nama, No. HP, dan Alamat dengan sistem Dialog yang intuitif.
* **Smart Photo Upload:** Ambil foto langsung dari **Kamera** atau **Galeri**.
* **Auto Compression:** Mengecilkan ukuran foto secara otomatis sebelum diunggah untuk menghemat kuota dan mempercepat proses.
* **Cache Busting:** Foto profil diperbarui secara instan di layar tanpa perlu refresh manual.

---

## 🛠️ Tech Stack

### Client Side (Android)
- **Language:** Kotlin
- **UI:** Jetpack Compose (Declarative UI)
- **Asynchronous:** Kotlin Coroutines & Flow
- **Networking:** Retrofit 2 & OkHttp3
- **Image Loader:** Coil (dengan sistem image signature)
- **Navigation:** Jetpack Navigation Component
- **DI & Architecture:** ViewModel, State Hoisting, Repository Pattern

### Backend Side
- **API Framework:** Laravel 12 (RESTful API)
- **AI Engine:** FastAPI (Python)
- **Database:** MySQL

---

## ⚙️ Cara Menjalankan Project

1. **Clone Repository:**
   ```bash
   git clone [https://github.com/username/mysawah-android.git](https://github.com/username/mysawah-android.git)

2. **Konfigurasi API: Buka file data/remote/ApiConfig.kt dan sesuaikan IP address server Anda:**
   ```bash
   const val LARAVEL_URL = "[http://192.168.](http://192.168.)x.x:8000/api/v1/"

3. **Android Manifest: Pastikan authorities pada FileProvider sesuai dengan package name Anda:**
   ```bash
   android:authorities="${applicationId}.provider"

4. **Build Project**

---

📂 **Struktur Project**
```bash
com.mysawah.predict
├── data
│   ├── local      # Token & Session Storage
│   ├── remote     # Retrofit Config & ApiService
│   └── repository # Auth, Cart, & Transaction Logic
├── domain
│   └── model      # Data Classes (User, Product, Cart, Chat, dll)
└── ui
    ├── components # Reusable Components (Dialogs, Cards, NavBars)
    ├── navigation # NavHost Configuration
    ├── screens    # Screen UI & ViewModels
    └── theme      # Typography, Colors, & Shapes

