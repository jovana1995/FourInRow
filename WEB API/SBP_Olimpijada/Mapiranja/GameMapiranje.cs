using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SBP_Olimpijada.Entiteti;
using FluentNHibernate.Mapping;

namespace SBP_Olimpijada.Mapiranja
{
    class GameMapiranje : ClassMap<SBP_Olimpijada.Entiteti.Game>   //ne mora da bude public ali mora da nasledi classmap(klasa koju mapiramo)
    {
        public GameMapiranje()  //sva mapiranja idu u konstruktor
        {
            //Mapiranje tabele
            Table("GAME");

            //mapiranje primarnog kljuca
            Id(x => x.Id_game, "ID_GAME").GeneratedBy.TriggerIdentity(); 

            //mapiranje svojstava
            Map(x => x.Game_date, "GAME_DATE");

            HasMany(x => x.players).KeyColumn("ID_GAME").Cascade.All().Inverse();
        }
    }
}
