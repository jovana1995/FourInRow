using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SBP_Olimpijada.Entiteti;
namespace SBP_Olimpijada.DTOs
{
    public class statisticView
    {
        public statisticView()
        { }
        public int Id_statistic { get; set; }
        public string Statistic_date { get; set; }
        public int Wins { get; set; }
        public int Defeats { get; set; }

        public statisticView(Statistic d)
        {
            this.Id_statistic = d.Id_statistic;
            this.Statistic_date = d.Statistic_date;
            this.Wins = d.Wins;
            this.Defeats = d.Defeats;
            
        }
    }
}