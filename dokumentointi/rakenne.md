Rakennekuvaus
===========

Peli koostuu kolmesta pääosasta, joista ensimmäinen on pelihahmoihin ja toiminnallisuuksiin liittyvät luokat ja pelin logiikka, toiseen käyttöliittymään ja piirtoon liittyvät luokat ja kolmantena event handlerit jotka välittävät tietoa pelin tapahtumista käyttöliittymälle ja käyttöliittymässä tapahtuvista pelaajan valinnoista pelille.

### Logiikka

Peli pyörii Game ja Turn luokkien ympärillä, jotka yhdessä hallinoi pelivuoroja.

Logiikkaan kuuluvia luokkia on lisäksi Collision luokka, jonka staattisia metodeja voidaan käyttää selvittäessä törmääkö kaksi liikkuvaa objektia toisiinsa. SurvivorSelector luokka sisältää logiikan jolla selvitetään survivorien valinnat canvasta klikattaessa.

ResourceEvents luokka säilyttää kaikki EventHandlerit, joita käytetään kun pelin tietoja päivitetään käyttöliittymään. Logiikka hoitaa myös zombien tiputtaman lootin jakelut, eri tyyppisille resursseille on omat luokkansa. Pelaajan resurssit säilytetään Inventory luokassa.

Pelihahmot zombit ja survivorit perivät MovingObject luokan, joka sisältää hahmojen liikkumiseen liittyvät perusfunktiot. Pelihahmot, kuten myös ansat ja luodit toteuttavat rajapinnan DrawableObject, joka takaa että objektit voidaan piirtää.

Weapons luokka sisältää melee aseiden toiminnot ja Firearm luokka ampuma-aseiden. Jokaiselle aseelle luodaan oma luokka, joka perii yläluokan. Ampuma-aseiden luodit säilytetään asekohtaisessa lippaassa (Magazine), ja Bullet luokan ilmentymä luodaan vasta kun aseella ammutaan. Myös luodit liikkuu MovingObjectin logiikalla.

Kaikki seinät ja kaikki ansat on periaatteessa samojen luokkien toteutuksia, mutta luokkien lähtöarvot haetaan enumista, joka sisältää kaikkien sienien ja ansojen yksilöidyt ominaisuudet. Lisäksi rakennuksilla on Cost luokka, joka sisältää niiden hinnan.

### Käyttöliittymä

UserInterface luokka sisältää käyttöliittymän ikkunat ja pelin ja käyttöliittymän TimeLine objektit.

Pelin tapahtumat näytetään pelaajalle GameCanvas luokan kautta, joka sisältää myös eventit, joiden avulla pelaajalle esitetään pelaajien valitseminen ja rakennusten rakentaminen. Canvas kutsuu pelialueen piirtoa varten  ObjectsDrawer ja ConstructionHoverDrawer luokkia.

Canvaksen lisäksi pelin käyttöliittymään kuuluu ResourcesPanel, jossa näytetään pelaajalle pelaajan hallinnoimat resurssit. ResourcesPanle toteuttaa useita event handlereita, joiden avulla paneelille annetaan tieto muuttaa resursseja pelissä tapahtuvien muutosten mukaisesti. Paneeli myös triggeröi eventtejä, esimerkiksi kun pelaaja vaihtaa survivorin asetta.

Toinen paneeli joka pelaajalle näytetään on BuildPanel, joka sisältää tiedot seinistä ja ansoista joita pelaaja pystyy rakentamaan. Kolmas paneeli on MessagePanel, jossa pelaajalle esitetään viestejä pelin etenemisestä.

### Eventit

Event handlerit on toteutettu rajapintoina, jotka ResourcePanel toteuttaa. Näin resurssipaneelille saadaan tieto, kun pelin resursseja muutetaan ja käyttöliittymää saadaan eriytettyä logiikasta.

Action eventit sisältää toiminnot, kuten ansojen ja seinien rakentamiset. Lisäksi uusien selviytyjien lisäys ja kuolleiden poisto ResourcesPanelista.

Mouse eventeillä sen sijaan esitetään pelaajalle seinien ja ansojen rakentamiseen liittyvät haamukuvat, sekä selviytyjien valitsemisen. Lisäksi pelissä on KeySelectEvent, jonka avulla hahmoja voi valita näppäinkomennoilla tai aktivoida Michonnen peliin.
