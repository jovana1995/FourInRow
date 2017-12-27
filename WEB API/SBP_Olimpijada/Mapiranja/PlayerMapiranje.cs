using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SBP_Olimpijada.Entiteti;
using FluentNHibernate.Mapping;

namespace SBP_Olimpijada.Mapiranja
{
    class PlayerMapiranje : ClassMap<SBP_Olimpijada.Entiteti.Player>   //ne mora da bude public ali mora da nasledi classmap(klasa koju mapiramo)
    {
        public PlayerMapiranje()  //sva mapiranja idu u konstruktor
        {
            //Mapiranje tabele
            Table("PLAYER");

            //mapiranje primarnog kljuca
            Id(x => x.Id_player, "ID_PLAYER").GeneratedBy.TriggerIdentity();  //properti koji je kljuc se mapira na primarni kljuc i on se generise uz pomoc trigera

            //mapiranje svojstava
            Map(x => x.First_name, "FIRST_NAME");        //properti na kolonu se mapira
            Map(x => x.Last_name, "LAST_NAME");
            Map(x => x.Date_of_birth, "DATE_OF_BIRTH");
            Map(x => x.Gender, "GENDER");
            Map(x => x.Email, "EMAIL");
            Map(x => x.Active, "ACTIVE");

            References(x => x.PlaysGame).Column("ID_GAME").LazyLoad();

           // HasMany(x => x.has).KeyColumn("ID_PLAYER").Cascade.All().Inverse();
        }
    }
}
