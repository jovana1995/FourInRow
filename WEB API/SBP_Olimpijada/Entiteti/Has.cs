using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SBP_Olimpijada.Entiteti
{
    public class Has
    {
        public virtual int Id_has { get; protected set; }   //mozemo da dodamo propertije koji se ne slikaju u bazu

        public virtual Statistic StatisticHas { get; set; }
        public virtual Player PlayerHas { get; set; }
    }
}
