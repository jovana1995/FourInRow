using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SBP_Olimpijada.Entiteti
{
    public class Game
    {
        public virtual int Id_game { get; set; }   //mozemo da dodamo propertije koji se ne slikaju u bazu
        public virtual string Game_date { get; set; }

        public virtual IList<Player> players { get; set; }  //ovo je interfejs zbog NHibernate

        public Game()
        {
            players = new List<Player>();
        }
    }
}

