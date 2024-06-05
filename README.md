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

### Interfejs graficzny:
Interfejs graficzny jest realizowany przy pomocy biblioteki Swing oraz Awt. <br>
Na dole ekranu znajdują się pola tekstowe odpowiednio: <br>
Ilość pięter (maksymalne piętro to ilość pięter - 1), Ilość wind (numerowanych od 0), ilość automatycznych zapytań na iterację<br>
Niżej znajdują się dwa przyciski służące do uruchamiania różnych typów symulacji. <br>
Na samym dole znajduje się pole tekstowe służące do obsługi trybu manualnego, wejścia można potwierdzać zarówno klawiszem Enter jak i przyciskiem znajdującym się obok. Gdy to pole jest puste wyświetla się podpowiedź.

Na górze ekranu po uruchomieniu symualcji będzie wyświetlała się tablica, pokazująca aktualny stan każdej z wind oraz piętro na którym się znajduje. <br>
Kolor windy reprezentuje jej stan: <br>
<ul>
  <li>${\color{green}zielony}$ to winda jadąca do góry, <br></li>
  <li>${\color{red}czerwony}$ to winda jadąca w dół, <br></li>
  <li>${\color{yellow}żółty}$ to winda która aktualnie ma otwarte drzwi i pasażerowie mogą wsiadać i wysiadać, <br></li>
  <li>${\color{blue}niebieski}$ to winda która nie ma aktualnie zajęcia.<br></li>
</ul>
Domyślnie krok symulacji jest ustawiony na 100ms, a czas oczekiwania na piętrze na 300ms. Te wartości mogą się zmieniać w zależności od ilości pięter oraz wind.

### Obsługa błędnych wejść wprowadzonych przez użytkownika:
Jeżeli użytkownik wprowadzi błędne dane w którymkolwiek z 2 pierwszych pól odpowiedzialnych za parametry symulacji symulacja nie wystartuje. <br>
W przypadku błędnie wprowadzonego pola ilości automatycznych zapytań na iterację tylko symulacja manualna wystartuje, a automatyczna nie. <br>
Błędnie wprowadzone manualne zapytanie jest odrzucane - pole nie jest zerowane.

### Złożoność i optymalizacja:
Algorytm w każdej iteracji wykonuje (ilość wind)x(ilość aktualnych stopów i-tej windy)x(ilość zapytań w iteracji) kroków co w zupełności wystarcza na obsłużenie 16, a nawet większej ilości wind.
Warto zauważyć, że ilość kroków nie jest zależna od ilości pięter, co umożliwia dobre skalowanie jeżeli chodzi o wysokość budynku.
Prawdopodobnie istnieją lepsze algorytmy optymalizujące kolejkowanie wind - dobrym pomysłem w rzeczywistym scenariuszu mogłoby być użycie Constraint Programming na przykład w języku MiniZinc, z uwagi na niewielką ilość danych do przetworzenia algorytm szybko znalazłby najoptymalniejsze rozwiązanie.

### Uruchomienie projektu:
<ul>
  <li>Sklonuj repozytorium</li>
  <li>Otwórz w wybranym przez siebie IDE (InteliJ na pewno działa). Projekt jest napisany w Java 21</li>
  <li>Uruchom Main</li>
</ul>
