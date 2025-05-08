# CyberScan Link

CyberScan Link é uma plataforma de varredura e análise de rede local, combinando a robustez do backend Java (Spring Boot) com o poder de análise de pacotes do Python (Scapy). Ele permite detectar dispositivos conectados, identificar portas abertas e exibir os resultados em uma interface web limpa.

## 🧠 Tecnologias
- Java 17 + Spring Boot 3
- Python 3.11 com `scapy`
- HTML com Thymeleaf
- REST API entre Java ↔ Python

## 🚀 Funcionalidades
- Varredura da rede local (ARP Scan)
- Detecção de IP, MAC, hostname e portas abertas
- Interface web moderna com botão de varredura
- Resultado dinâmico exibido na mesma tela
- Backend REST chamando scripts Python com retorno JSON

## ▶️ Como executar

### Instale dependências:
```bash
pip install -r python/requirements.txt
```

### Execute o backend:
```bash
cd java
./mvnw spring-boot:run
```

### Acesse:
- Interface Web: [http://localhost:8080](http://localhost:8080)
- REST puro: [http://localhost:8080/scan/start](http://localhost:8080/scan/start)

## 📸 Interface

Clique em “Iniciar Varredura” e veja a lista de dispositivos conectados em tempo real.

## 📂 Estrutura
```
cyberscan-link/
├── java/                       # Backend Spring Boot
│   └── src/main/java/com/cyberscan/...
│   └── resources/templates/    # Interface HTML
├── python/                     # Scanner Python
├── reports/                    # (Opcional) saída futura
├── README.md
```

## 🔐 Requisitos
- Java 17+
- Python 3.10+
- Linux ou Windows com permissões de rede

## ✨ Futuro
- Geolocalização de IP
- Armazenamento de histórico (PostgreSQL)
- Exportação de relatórios (PDF/CSV)