# 🚀 Email Assistant — AI-Powered Email Reply Generator

An AI-powered Chrome Extension that generates smart, context-aware email replies directly inside Gmail.
Built using **Spring Boot (WebFlux)** for backend, **React + Vite** for the UI, and **Groq LLM API** for ultra-fast, low-latency inference.

---

## 🌟 Features

* ✨ **One-click AI email reply generation**
* ⚡ **Powered by Groq LLaMA / Mixtral models via Spring AI**
* 🔄 **Real-time backend streaming using Spring WebFlux**
* 🔌 **Chrome Extension injected directly into Gmail UI**
* 🧩 **Editable prompts + tone selection**
* 🎨 **Responsive React UI (Vite)**
* 🔐 **Secure API handling using environment variables**

---

## 🏛 Architecture Overview

```
Gmail DOM → Chrome Extension → React UI → Spring Boot API → Groq LLM → Reply Returned → Injected into Gmail
```

---

## 🛠 Tech Stack

### **Frontend (Chrome Extension + React + Vite)**

* React + Vite
* Content Scripts + Manifest V3
* TailwindCSS (if used)
* Messaging between extension → service worker → app

### **Backend (Spring Boot)**

* Spring Boot 3+
* Spring WebFlux
* WebClientBuilder
* Spring AI (Groq Model Integration)
* CORS Enabled

### **AI**

* Groq API (LLaMA / Mixtral Models)

---

## 📁 Folder Structure (Recommended)

```
Email-Assistant/
│── email-assistant-sb(backend)/
│   ├── src/main/java/... (Spring Boot API)
│   ├── application.properties
│   └── pom.xml
│
│── email-assistant-ext(extension)/
│   ├── manifest.json
│   ├── content.js
│   ├── email-assistant.png    
│
│── email-assistant-react(ui)/
│   ├── vite.config.js
│   ├── package.json
│   └── src/
│       ├── index.css
│       ├── index.jsx
│       └── App.css
│       └── App.jsx
│
└── README.md
```

---

## ⚙️ Backend Setup (Spring Boot)

### **1️⃣ Clone the repository**

```
git clone https://github.com/ayushjha-aj23/email-assistant.git
cd backend
```

### **2️⃣ Add your Groq API Key**

Add to `application.yml`:

```yaml
spring:
  ai:
    groq:
      api-key: ${GROQ_API_KEY}
```

Or set environment variable:

```
export GROQ_API_KEY=your_key_here
```

### **3️⃣ Run the backend**

```
mvn spring-boot:run
```

Server runs at:

```
http://localhost:8080
```

---

## 🔌 API Endpoint

### **POST /api/generate-reply**

**Request Body:**

```json
{
  "emailContent": "Full email received",
  "tone": "formal/informal",
  "instructions": "Any specific prompts"
}
```

**Response:**

```json
{
  "reply": "AI-generated email response..."
}
```

---

## 🧩 Chrome Extension Setup

### **1️⃣ Load Extension in Chrome**

1. Open `chrome://extensions/`
2. Enable **Developer Mode**
3. Click **Load Unpacked**
4. Select the `extension/` folder

### **2️⃣ Inject into Gmail**

The content script automatically detects Gmail compose box and adds your **AI Reply button**.

---

## 🖥 React + Vite UI Setup

```
cd ui
npm install
npm run dev
```

This serves the extension popup UI or standalone preview UI.

---

## 📸 Screenshots (Add your images)

Add screenshots in a `/screenshots` folder and link below:

```
![Chrome Extension Popup](screenshots/popup.png)
![Gmail Integration](screenshots/gmail.png)
```

---

## 🚀 Future Improvements

* Add multi-tone reply generation
* Add email summarization
* Add context-based threading
* Store user preferences
* OAuth Gmail API integration (optional)
* Firefox extension support

---

## 🤝 Contributing

PRs are welcome!
Feel free to fork the repo and open issues.

---

## 📜 License

MIT License.

---

## 👨‍💻 Author

**Ayush Jha**
GitHub: [ayushjha-aj23](https://github.com/ayushjha-aj23)
