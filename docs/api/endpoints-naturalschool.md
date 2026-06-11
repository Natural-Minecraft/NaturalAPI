# NaturalSchool API Endpoints

Semua endpoint ini memerlukan header `Authorization: Bearer <TOKEN>` dengan scope `read:naturalschool` atau `*` (Admin).

## 1. Get Player School Data
Mengambil data profil akademik siswa yang terdaftar di sistem NaturalSchool.

**URL:** `GET /api/v1/naturalschool/player/{identifier}`

**Parameter Path:**
* `identifier`: UUID atau Username dari pemain.

**Response Berhasil (200 OK):**
```json
{
  "success": true,
  "data": {
    "nis": "10293",
    "academicStage": "SMA",
    "academicClass": 10,
    "currentSemester": "GANJIL",
    "rank": {
      "id": "KETUA_OSIS",
      "displayName": "Ketua OSIS",
      "priority": 1,
      "type": "MANAGEMENT"
    },
    "isStaff": false,
    "isManagement": true
  }
}
```

## 2. Refresh/Sync Data (Event Handler)
Endpoint yang dapat digunakan oleh plugin NaturalSchool (atau sistem eksternal) untuk memicu pembaruan data/snapshot pemain ke dalam database NaturalAPI secara paksa, memastikan data yang ditarik dari endpoint lain tetap *real-time*.

**URL:** `POST /api/v1/naturalschool/refresh`

**Body (Opsional, `application/json`):**
```json
{
  "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f"
}
```
*Catatan: Jika `uuid` disertakan, API akan memaksa update/simpan ulang snapshot untuk pemain tersebut.*
*Jika kosong, API mencoba reload integrasi secara general.*

**Response Berhasil (200 OK):**
```json
{
  "success": true,
  "data": {
    "message": "Player snapshot update triggered successfully.",
    "uuid": "eea40d5a-6e98-3de2-9a0d-631505df935f"
  }
}
```
