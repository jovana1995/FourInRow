using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SBP_Olimpijada.Entiteti;
using FluentNHibernate.Mapping;

namespace SBP_Olimpijada.Mapiranja
{
    class HasMapiranje : ClassMap<SBP_Olimpijada.Entiteti.Has>   //ne mora da bude public ali mora da nasledi classmap(klasa koju mapiramo)
    {
        public HasMapiranje()  //sva mapiranja idu u konstruktor
        {
            //Mapiranje tabele
            Table("HAS");

            //mapiranje primarnog kljuca
            Id(x => x.Id_has, "ID_HAS").GeneratedBy.TriggerIdentity();  //properti koji je kljuc se mapira na primarni kljuc i on se generise uz pomoc trigera

            //mapiranje svojstava
            References(x => x.PlayerHas).Column("ID_PLAYER").LazyLoad();
            References(x => x.StatisticHas).Column("ID_STATISTIC").LazyLoad();
        }
    }
}
