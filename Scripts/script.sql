create table Restaurants(
idRestaurant number(5),
nomRestaurant varchar(100),
adresseRestaurant varchar(100),
qualiteBouffe varchar(20),
qualiteService varchar(20),
prixMoyen number(6,2),
nbEtoiles number(1),
CONSTRAINT pk_resto PRIMARY KEY(idRestaurant));

commit;

select * from restaurants;

commit;

select * from myrestaurants;

drop view myrestaurants;

create view myrestaurants as
select idRestaurant, nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = r.qualiteBouffe) bouffe, 
(select idQuality from qualitymap where nomQuality = r.qualiteService) service,
prixMoyen, nbEtoiles ,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 5) fivestar
from restaurants r;

commit;

create view viewrestaurants as select * from restaurants;

select * from viewrestaurants;

grant select on viewrestaurants to GauthierB;

select * from GauthierB.orarestaurant;

create view myrestaurants2 as
select nomRestaurant, adresseRestaurant, 
avg(bouffe) as avgbouffe, avg(service) as avgservice, 
avg(prixMoyen) as avgprice, avg(nbEtoiles) as avgetoiles,
sum(nbvotes) as nbvotes,
sum(onestar) as onestar, sum(twostar) as twostar, 
sum(treestar) as treestar, sum(fourstar) as fourstar, sum(fivestar) as fivestar from
(select nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = lower(r.qualiteBouffe)) bouffe, 
(select idQuality from qualitymap where nomQuality = lower(r.qualiteService)) service,
prixMoyen, nbEtoiles,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.nbetoiles = 5) fivestar
from viewrestaurants r union all
select nomRestaurant, adresseRestaurant, 
(select idQuality from qualitymap where nomQuality = lower(r2.qualiteBouffe)) bouffe, 
(select idQuality from qualitymap where nomQuality = lower(r2.qualiteService)) service,
prixMoyen, nbEtoiles,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant) nbvotes,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r2.nomRestaurant and rs.nbetoiles = 5) fivestar
from GauthierB.orarestaurant r2) group by nomRestaurant, adresseRestaurant;

insert into restaurants values(5, 'restotest', 'avenue fictive', 'Horrible', 'Horrible', 0, 2);
drop view myrestaurants2;
select * from myrestaurants2;

grant select on viewrestaurants to public;

 CREATE TABLE "FALARDEA"."QUALITYMAP" 
   (	"IDQUALITY" NUMBER(1,0), 
	"NOMQUALITY" VARCHAR2(20 BYTE), 
	 CONSTRAINT "PK_QUALITYMAP" PRIMARY KEY ("IDQUALITY")