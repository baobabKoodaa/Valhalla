# Aihemäärittely

Runko vuoropohjaiselle strategiapelille, jonka maailma esitetään ruuduista koostuvana karttana. Tähän runkoon on toteutettu myös reaaliaikainen simulaatio madoista ja nanoboteista. Botit tutkivat maastoa ja syövät sieltä löytyvää ruokaa. Mato kasvaa syödessään, nanobotti monistaa itsensä.

# Rakennekuvaus

Karkeasti ottaen ohjelma jakautuu GUI:hin, game looppiin ja maailmaan. Maailman tila (State) koostuu soluista (Cell), joissa voi olla Elementtejä. Elementit, jotka löytyvät "representation" -paketista, ovat ikään kuin esityspalikoita alla olevalle todellisuudelle, joka löytyy "underlying" paketista. Toisin sanoin halusin jakaa luokkien vastuut niin, että yksi luokka "on" jotakin, toinen luokka "esittää" sitä solun päällä. State koostuu soluista ja GUI pyytää siltä soluja, kun se haluaa piirtää jotakin. Piirtämistä pyydetään sekä game loopin toimesta että käyttäjän (esim. näkymän siirtäminen hiirellä).

# Sekvenssikaaviot

![Sekvenssikaaviot](/dokumentointi/sekvenssikaaviot.jpg "Sekvenssikaaviot")
