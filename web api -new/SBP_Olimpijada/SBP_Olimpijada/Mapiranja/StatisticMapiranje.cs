using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SBP_Olimpijada.Entiteti;
using FluentNHibernate.Mapping;

namespace SBP_Olimpijada.Mapiranja
{
    class StatisticMapiranje : ClassMap<SBP_Olimpijada.Entiteti.Statistic>   //ne mora da bude public ali mora da nasledi classmap(klasa koju mapiramo)
    {
        public StatisticMapiranje()  //sva mapiranja idu u konstruktor
        {
            //Mapiranje tabele
            Table("STATISTIC");

            //mapiranje primarnog kljuca
            Id(x => x.Id_statistic, "ID_STATISTIC").GeneratedBy.TriggerIdentity();  //properti koji je kljuc se mapira na primarni kljuc i on se generise uz pomoc trigera

            //mapiranje svojstava
            Map(x => x.Statistic_date, "STATISTIC_DATE");        //properti na kolonu se mapira
            Map(x => x.Wins, "WINS");
            Map(x => x.Defeats, "DEFEATS");
            References(x => x.HasPlayer).Column("ID_PLAYER").LazyLoad();

        }
    }
}