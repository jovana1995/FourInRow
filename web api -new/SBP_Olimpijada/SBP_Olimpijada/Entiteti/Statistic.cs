using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SBP_Olimpijada.Entiteti
{
    public class Statistic
    {
        public virtual int Id_statistic { get; set; }   //mozemo da dodamo propertije koji se ne slikaju u bazu
        public virtual string Statistic_date { get; set; }
        public virtual int Wins { get; set; }
        public virtual int Defeats { get; set; }
        public virtual Player HasPlayer { get; set; }


    }
}
