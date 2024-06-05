# Elevator Controller

### Przyjęte podejście:
Dwa typy zapytań: Request oraz InteriorRequest <br>
Request jest zapytaniem zewnętrznym i odpowiada wciśnięciu przycisku chęci jazdy w górę lub w dół na danym piętrze. <br>
InteriorRequest jest zapytaniem wewnętrznym, kierowanym do konkretnej windy i wyraża chęć jazdy na konkretne piętro. <br>

Kontroler windy stara się rozdzielić pracę wind tak, aby zminimalizować czas oczekiwania przez klienta na przybycie windy po otrzymaniu zapytania typu Request. <br>
Robi to poprzez znalezienie windy, która jest najbliżej piętra przywołującego, z zachowaniem pewnych reguł:
<ul>
  <li>Jeżeli winda nie ma żadnego celu lub jedzie w tym samym kierunku, w którym chce jechać osoba przywołująca to <br> dystans = |aktualne piętro - piętro przywołujące|</li>
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
    <li>kierunek, w którym chcemy się poruszać [spacja] piętro przywołujące - Request, optymalizowany</li>
  </ul>
  </li>
</ul>

### Interfejs graficzny:
Interfejs graficzny jest realizowany przy pomocy biblioteki Swing oraz Awt. <br>
Na dole ekranu znajdują się pola tekstowe odpowiednio: <br>
Ilość pięter (maksymalne piętro to ilość pięter - 1), Ilość wind (numerowanych od 0), ilość automatycznych zapytań na iterację<br>
Niżej znajdują się dwa przyciski służące do uruchamiania różnych typów symulacji. <br>
Na samym dole znajduje się pole tekstowe służące do obsługi trybu manualnego, wejścia można potwierdzać klawiszem Enter. Gdy to pole jest puste wyświetla się podpowiedź.

Na górze ekranu po uruchomieniu symualacji będzie wyświetlała się tablica, pokazująca aktualny stan każdej z wind oraz piętro, na którym się znajduje. <br>
Kolor windy reprezentuje jej stan: <br>
<ul>
  <li>zielony to winda jadąca do góry, <br></li>
  <li>czerwony to winda jadąca w dół, <br></li>
  <li>żółty to winda, która aktualnie ma otwarte drzwi i pasażerowie mogą wsiadać i wysiadać, <br></li>
  <li>niebieski to winda, która nie ma aktualnie zajęcia.<br></li>
</ul>
Domyślnie krok symulacji jest ustawiony na 100ms, a czas oczekiwania na piętrze na 300ms. Te wartości mogą się zmieniać w zależności od ilości pięter oraz wind.

### Obsługa pliku CSV:
Dodałem obsługę plików CSV jako plików z danymi. Powinny one zawierać takie same zapytania jak wpisywanie ręczne, oddzielone przecinkami np.: <br>
```U 5,0 3,D 7,D 3,...``` <br>
Umożliwiają one odgrywanie "scenariuszy" ruchów wind. Każde wejście jest czytane z pliku co stały krok czasowy (aktualnie 333ms) co jest dużo szybsze niż wpisywanie ręczne.

### Obsługa błędnych wejść wprowadzonych przez użytkownika:
Jeżeli użytkownik wprowadzi błędne dane w którymkolwiek z 2 pierwszych pól odpowiedzialnych za parametry symulacji to symulacja nie wystartuje. <br>
W przypadku błędnie wprowadzonego pola ilości automatycznych zapytań na iterację tylko symulacja manualna wystartuje, a automatyczna nie. <br>
Błędnie wprowadzone manualne zapytanie jest odrzucane - pole nie jest zerowane.

### Złożoność i optymalizacja:
Algorytm w każdej iteracji wykonuje (ilość wind)x(ilość aktualnych stopów i-tej windy)x(ilość zapytań w iteracji) kroków co w zupełności wystarcza na obsłużenie 16, a nawet większej ilości wind. <br>
Warto zauważyć, że ilość kroków nie jest zależna od ilości pięter, co umożliwia dobre skalowanie jeżeli chodzi o wysokość budynku. <br>
Prawdopodobnie istnieją lepsze algorytmy optymalizujące kolejkowanie wind - dobrym pomysłem w rzeczywistym scenariuszu mogłoby być użycie Constraint Programming na przykład w języku MiniZinc, z uwagi na niewielką ilość danych do przetworzenia algorytm szybko znalazłby najoptymalniejsze rozwiązanie. <br>
Testowałem również dodatkową optymalizację w postaci liczenia ilości zatrzymań które wykona winda zanim dotrze na wołające piętro i dodawania ich (pomnożonych razy 3) do dystansu, ale z moich obserwacji wynika, że nie usprawnia to automatycznego działania, więc zrezygnowałem z tego sposobu.

### Uruchomienie projektu:
<ul>
  <li>Sklonuj repozytorium</li>
  <li>Otwórz w wybranym przez siebie IDE (InteliJ na pewno działa). Projekt jest napisany w Oracle OpenJDK 21, ale można go uruchomić używając Oracle OpenJDK 17</li>
  <li>Uruchom Main</li>
</ul>
Zmiana prędkości symulacji jest możliwa poprzez zmianę wartości parametru simulationSpeed w klasie Main.

### Uwaga! Przykładowy plik csv należy uruchomić dla >6 pięter oraz >3 wind!
