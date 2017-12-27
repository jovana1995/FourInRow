using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SBP_Olimpijada.Entiteti;
namespace SBP_Olimpijada.DTOs
{
    public class hasView
    {
        public int Id_has { get; protected set; } 

        public  string PlayerHas { get; set; }
        public  string StatisticHas { get; set; }

        public hasView(Has has)
        {
            this.Id_has = has.Id_has;
            //this.StatisticHas = has.StatisticHas.Id_statistic;
            this.PlayerHas = has.PlayerHas.First_name+" "+has.PlayerHas.Last_name;

        }
    }
}
