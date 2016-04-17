# Viikon 1 aihemäärittely

**Aihe:** Runko vuoropohjaiselle strategiapelille, jonka maailma esitetään ruuduista koostuvana karttana.

**Tarkemmin:** Ohjelman muistissa on kartta, jossa jokaiselle ruudulle on tiedossa maasto sekä mahdollisesti maaston päällä olevia elementtejä (esim. rakennus, yksikkö, resurssi, tai tutkimattoman maaston peittävä musta neliö). Kartta on suuri ja siitä voidaan siten piirtää päänäkymään vain osa. Alakulmassa näkyvään minimappiin piirretään kartta kokonaan. Koska elementit voivat sisältää läpinäkyviä osia, piirretään jokaisen ruudun elementit päällekkäin järjestyksessä aloittaen maastosta. Lisäksi päänäkymän reunoille piirretään työkalupalkkeja, joihin voi sijoittaa toimintoja.

**Pelaajan toiminnot:**
- Päänäkymään piirrettävän alueen valitseminen klikkaamalla minimapista
- Päänäkymässä sisään/ulos zoomaaminen portaattomasti (portaattomuus=päänäkymän reunoilla olevat ruudut piirretään yleensä vain osittain)
- Päänäkymän "liikuttaminen" hiirellä hilaamalla portaattomasti
- Ruudun valitseminen klikkaamalla päänäkymästä (esim. yksikön liikuttamista varten)
- Työkalupalkkien klikkaaminen (esim. vuoron päättäminen painamalla tiimalasia esittävää kuvaa)

# Viikon 2 lisäyksiä

- Reaaliaikainen gamelooppi
- Tekoälyllä liikkuvat organismit, joista toistaiseksi toteutettu Nanobot ja Worm.
- Tällä hetkellä ohjelma pyörii simulaationa, jossa organismit syövät kartalta löytyvää ruokaa ja monistautuvat tai kasvavat lajikkeesta riippuen.

# Viikko 5 sekvenssikaaviot

![Sekvenssikaaviot](/dokumentointi/sekvenssikaaviot.jpg "Sekvenssikaaviot")
