# Fog-Projektet
Semesterprojekt Lukas og Rasmus HS

Fog-Projektet er et Java-baseret system udviklet til at designe og generere tilpassede carporte og tilhørende materialelister til DIY-kunder. Projektet understøtter salgspersonale ved at generere SVG-tegninger og en detaljeret materialeliste.

----------------------------------

Sådan køres projektet

1: Opsætning af database:

  1a: Installér docker desktop, og pgadmin container version.
  
  1b: Tilpas din docker-compose.yml fil til dit system
  
  1c: Åbn git bash I samme folder som din docker-compose.yml fil, og skriv "docker compose up -d"
  
  1d: Tilgå pgadmin via localhost med port og loginoplysninger du angav i docker-compose.yml, opret en server med en database, med et schema "public.", og kør script.sql på det schema
  
  
2: Kørsel af java app:

  2a: Clon dette repository og åbn det i din foretrukne IDE
  
  2b: Tilpas din conncetion pool i main klassen til at tilsvare din docker-compose.yml fil
  
  3c: Start applikationen ved at køre main metoden i main-klassen
  
  4d: Åbn en browser og gå til http://localhost:3484 (eller den port du har angivet på linje 30 i main klassen).

----------------------------------
  
Mappestruktur: 

Fog-Projektet/

│

├── doc/                 # Planlægningsfiler og diagrammer

│   ├── plan/            # Class og domain modeller (PlantUML)

│   └── docs/            # ERD og andre diagrammer

│

├── src/

│   ├── main/

│   │   ├── java/        # Java-kode

│   │   │   ├── app/

│   │   │       ├── config/        # Konfigurationsklasser

│   │   │       ├── controller/    # Controller-lag (HTTP-håndtering)

│   │   │       ├── entities/      # Entity-klasser (Domænemodeller)

│   │   │       ├── exception/     # Fejlhåndtering

│   │   │       ├── persistence/   # Databaseadgang (DAOs)

│   │   │       ├── services/      # Service-lag (Forretningslogik)

│   │   │       └── Main           # Hovedapplikationen

│   │   ├── resources/

│   │       ├── public/    # Frontendfiler (CSS, billeder)

│   │       ├── sql/       # SQL-scripts

│   │       └── templates/ # Thymeleaf HTML-skabeloner

│   │

│   ├── test/             # Testkode

│       ├── java/         # JUnit-tests til persistence og services

│

└── .gitignore            # Git ignore-fil

----------------------------------

For mere information, læs venligst den fulde rapport: https://github.com/RHoejholt/Fog-Projektet/blob/c71d5f1f1f594294ab62be1e62707ab150010b9e/Fog-projektet.pdf
Google docs version af rapporten, med bookmark funktionalitet https://docs.google.com/document/d/1uOY6E59AhleiFHnUvHrP86kKKO3EuZKv-fymhLtT0Nk/edit?usp=sharing
