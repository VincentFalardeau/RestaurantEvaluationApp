create table Restaurants(
idRestaurant number(5),
nomRestaurant varchar(100),
adresseRestaurant varchar(100),
qualiteBouffe varchar(20),
qualiteService varchar(20),
prixMoyen number(6,2),
nbEtoiles number(1),
CONSTRAINT pk_resto PRIMARY KEY(idRestaurant));

create sequence seqresto increment by 1 start with 29;

create or replace 
trigger airesto 
before insert on restaurants
for each row
begin
    :new.idRestaurant := seqresto.nextval();
end;

create table QualityMap(
idquality NUMBER(1),
nomquality varchar(20),
constraint pk_qualitymap primary key (idquality));

create view viewrestaurants as select * from restaurants;

grant select on viewrestaurants to public;





select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = r.qualiteBouffe)) bouffe, 
avg((select idQuality from qualitymap where nomQuality = r.qualiteService)) service,
avg(prixMoyen), avg(nbEtoiles),
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from restaurants r group by nomRestaurant, adresseRestaurant;

select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = (replace(lower(r.qualiteBouffe), 'é', 'e')))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = (replace(lower(r.qualiteService), 'é', 'e')))) service,
avg(prixMoyen), avg(nbEtoiles),
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from GauthierB.orarestaurant r group by nomRestaurant, adresseRestaurant;

select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen), avg(nbEtoiles),
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from lanthies.restaurant r group by nomRestaurant, adresseRestaurant;

select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen), avg(nbEtoiles),
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from michaudj.restaurant r group by nomRestaurant, adresseRestaurant;

select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen), avg(nbEtoiles),
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from lemarbre.restaurant r group by nomRestaurant, adresseRestaurant;







create view myrestaurants as 
select nomRestaurant, adresseRestaurant, 
avg(bouffe) as avgbouffe, avg(service) as avgservice, 
avg(prixMoyen) as avgprice, avg(nbEtoiles) as avgetoiles,
sum(nbvotes) as nbvotes,
sum(onestar) as onestar, sum(twostar) as twostar, 
sum(treestar) as treestar, sum(fourstar) as fourstar, sum(fivestar) as fivestar
from
(select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = r.qualiteBouffe)) bouffe, 
avg((select idQuality from qualitymap where nomQuality = r.qualiteService)) service,
avg(prixMoyen) prixMoyen, avg(nbEtoiles) nbEtoiles,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from restaurants rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from restaurants r group by nomRestaurant, adresseRestaurant
union all
select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = (replace(lower(r.qualiteBouffe), 'é', 'e')))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = (replace(lower(r.qualiteService), 'é', 'e')))) service,
avg(prixMoyen) prixMoyen, avg(nbEtoiles) nbEtoiles,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from GauthierB.orarestaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from GauthierB.orarestaurant r group by nomRestaurant, adresseRestaurant
union all
select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen) prixMoyen, avg(nbEtoiles) nbEtoiles,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from lanthies.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from lanthies.restaurant r group by nomRestaurant, adresseRestaurant
union all
select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen) prixMoyen, avg(nbEtoiles) nbEtoiles,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from michaudj.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from michaudj.restaurant r group by nomRestaurant, adresseRestaurant
union all
select nomRestaurant, adresseRestaurant, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteBouffe), 'é', 'e')), 'bon', 'bien'))) bouffe, 
avg((select idQuality from qualitymap where nomQuality = replace((replace(lower(r.qualiteService), 'é', 'e')), 'bon', 'bien'))) service,
avg(prixMoyen) prixMoyen, avg(nbEtoiles) nbEtoiles,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant) nbvotes,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 1) onestar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 2) twostar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 3) treestar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 4) fourstar,
(select count(*) from lemarbre.restaurant rs where rs.nomRestaurant = r.nomRestaurant and rs.adresseRestaurant = r.adresseRestaurant and rs.nbetoiles = 5) fivestar
from lemarbre.restaurant r group by nomRestaurant, adresseRestaurant) r
group by nomRestaurant, adresseRestaurant;

