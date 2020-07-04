# MVM-Android

Specifikacija projekta
APLIKACIJA ZA SUGERISANjE POLjOPRIVREDNE PROIZVODNjE

Osnovne funkcionalnosti, koje Aplikacija za sugerisanje poljoprivredne proizvodnje podržava, su:
* Registracija korisnika
*	Prijava na sistem
*	Ažuriranje profila korisnika
*	Uvid u forum, otvaranje diskusije i postavljanje komentara
*	Prikaz prediktovanih cena poljoprivrednih proizvoda
*	Prikaz sugerisane poljoprivredne proizvodnje
*	Postavljanje ponude za otkup poljoprivrednog proizvoda
*	Lociranje otkupljivača sa najboljom ponudom i notifikovanje korisnika
*	Podešavanje aplikacije

U procesu registracije, korisnik unosi informaciju o svojoj primarnoj poljoprivrednoj delatnosti, na osnovu koje mu se omogućuje uvid u sve diskusije pokrenute od strane korisnika sa istom delatnošću . Komentari tekstualnog ili multimedijalnog sadržaja, postavljeni od strane jednog korisnika, bivaju vidljivi svim drugim korisnicima, koji pripadaju istoj poljoprivrednoj delatnosti. Pri tome, korisnik, koji je pokrenuo komentarisanu diskusiju, kao i ostali komentatori date diskusije, dobijaju notifikaciju sa informacijama o novom komentaru . Naravno, pored komentarisanja postojećih diskusija, korisnicima je omogućeno pokretanje novih. Izmenom informacije o poljoprivrednoj delatnosti na svom profilu, korisnik dobija uvid u filtriranu listu diskusija po kriterijumu novoizabrane poljoprivredne delatnosti. 
                          
  
Ukoliko je korisnik dozvolio aplikaciji da, pomoću servisa za lociranje, odredi njegovu lokaciju , te informacije bivaju evidentirane. 

U suprotnom, korisniku je omogućeno restriktivno korišćenje aplikacije, koje obuhvata sledeće: uvid u prediktovane cene i zahtevanje sugerisanja poljoprivredne proizvodnje. Predikcija cena vrši se na osnovu određenih statističkih podataka za narednu nedelju, te je neophodno samo da korisnik odabere jedan od ponuđenih proizvoda , za koje se predikcija vršila, što rezultira prikazom prediktovanih cena za najveće gradove u Srbiji, kao i vizualizacija fluktuacija cene datog proizvoda u datom gradu . Kako bi se podržala logika generisanja sugestije poljoprivredne proizvodnje, zahtev za sugestijom prati informacija o korisničkom stepenu opremljenosti resursima. 
 	       	      
       
Postavljanje otkupne ponude, odnosno, cene otkupnog proizvoda, kao i generisanje sugestije najpovoljnijeg prodajnog mesta, funkcionalnosti su, koje se baziraju na servisu za lociranje. Nakon što korisnik ponudi najvišu cenu za otkup određenog proizvoda, vrši se notifikovanje svih korisnika, koji su locirani u uskom krugu oko lokacije otkupljivača i koji su evidentirali istu poljoprivrednu delatnost, kao i otkupljivač. Zahtev za sugerisanjem prodajnog mesta rezultira prikazom mape i putanje do sugerisanog mesta , koje se generiše na osnovu otkupne cene i cene goriva, koje bi u proseku vozilo potrošilo krećući se od lokacije proizvođača do lokacije otkupljivača. 
 

Pored osnovnih funkcionalnosti, korisnicima je omogućeno i podešavanje aplikacije, koje se odnosi na pristizanje notifikacija.
