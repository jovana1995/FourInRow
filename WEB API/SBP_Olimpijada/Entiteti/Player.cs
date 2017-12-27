using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SBP_Olimpijada.Entiteti
{
    public class Player
    {
        public virtual int Id_player { get; set; }   //mozemo da dodamo propertije koji se ne slikaju u bazu
        public virtual string First_name { get; set; }
        public virtual string Last_name { get; set; }
        public virtual string Date_of_birth { get; set; }
        public virtual string Gender { get; set; }
        public virtual string Email { get; set; }
        public virtual string Active { get; set; }

        public virtual Game PlaysGame { get; set; }

        //public virtual IList<Has> has { get; set; }

        public Player()
        {
            //has = new List<Has>();
        }
    }
}
