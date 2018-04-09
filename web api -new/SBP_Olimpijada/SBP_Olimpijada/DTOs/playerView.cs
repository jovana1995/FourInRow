using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SBP_Olimpijada.Entiteti;
namespace SBP_Olimpijada.DTOs
{
    public class playerView
    {
        public int Id_player { get; set; }
        public string First_name { get; set; }
        public string Last_name { get; set; }
        public string Date_of_birth { get; set; }
        public string Gender { get; set; }
        public string Email { get; set; }
        public string Active { get; set; }
        public IList<statisticView> statistics { get; set; }

        public playerView(Player p)
        {
            try
            {
                this.Id_player = p.Id_player;
                this.First_name = p.First_name;
                this.Last_name = p.Last_name;
                this.Date_of_birth = p.Date_of_birth;
                this.Gender = p.Gender;
                this.Email = p.Email;
                this.Active = p.Active;

                //this.Hases = new List<hasView>();
                //foreach (Has u in p.has)
                //{
                //    this.Hases.Add(new hasView(u));
                //}
                this.statistics= new List<statisticView>();
                foreach (Statistic o in p.statistics)
                {
                    this.statistics.Add(new statisticView(o));
                }
            }
            catch(Exception e)
            {
                string ss= e.InnerException.Message.ToString();
            } 
        }
        public playerView() {}
    } 
}
