# Elevator Controller

### Przyjęte podejście:
Dwa typy zapytań: Request oraz InteriorRequest <br>
Request jest zapytaniem zewnętrznym i odpowiada wciśnięciu przycisku chęci jazdy w górę lub w dół na danym piętrze. <br>
InteriorRequest jest zapytaniem wewnętrznym, kierowanym do konkretnej windy i wyrażający chęć jazdy na konkretne piętro. <br>

Kontroler windy stara się rozdzielić pracę wind tak, aby zminimalizować czas oczekiwania przez klienta na przybycie windy po otrzymaniu zapytania typu Request. <br>
Robi to poprzez znalezienie windy która jest najbliżej piętra przywołującego, z zachowaniem pewnych reguł:
<ul>
  <li>Jeżeli winda nie ma żadnego celu lub jedzie w tym samym kierunku w którym chce jechać osoba przywołująca to <br> dystans = |aktualne piętro - piętro przywołujące|</li>
  <li>W przeciwnym wypadku (winda jedzie w przeciwnym kierunku) <br> dystans = |aktualne piętro - piętro docelowe| + |piętro docelowe - piętro przywołujące|</li>
  <li>Wybierana jest winda z najmniejszym dystansem</li>
</ul>
Zapytania typu InteriorRequest przez swoją naturę nie są optymalizowane gdyż jest to w tym podejściu niemożliwe.

### Dostępne typy symulacji:
Są dwa typy symulacji: <br>
<ul>
  <li>Automatyczna, w której zgodnie z liczbą wpisaną w polu Automatic Requests Per Iteration wysyła: <br>
  70% z tej liczby jako zapytania typu Request oraz 30% jako zapytania InteriorRequest próbując symulować rzeczywiste korzystanie z wind w budynku. Ten typ symulacji również akceptuje manualne zapytania lecz ciężko zauważyć ich efekt.</li>
  <li>W pełni manualna, w której jedyne zapytania są wprowadzane przez użytkownika. Wejście obsługuje dwa typy zapytań: <br>
  <ul>
    <li>numer windy (zaczynając od 0) [spacja] piętro docelowe - InteriorRequest, nieoptymalizowany</li>
    <li>kierunek w którym chcemy się poruszać [spacja] piętro przywołujące - Request, optymalizowany</li>
  </ul>
  </li>
</ul>

### Uruchomienie projektu:
<ul>
  <li>Sklonuj repozytorium</li>
  <li>Otwórz w wybranym przez siebie IDE</li>
  <li>Uruchom Main</li>
</ul>
