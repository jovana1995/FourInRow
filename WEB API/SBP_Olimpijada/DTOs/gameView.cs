using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SBP_Olimpijada.Entiteti;
namespace SBP_Olimpijada.DTOs
{
    public class gameView
    {
        public int Id_game { get; set; }
        public string Game_date { get; set; }
        public IList<playerView> Players{ get; set; }

        public gameView(Game g)
        {
            this.Id_game = g.Id_game;
            this.Game_date = g.Game_date;
            this.Players = new List<playerView>();
            foreach (Player o in g.players)
            {
                this.Players.Add(new playerView(o));
            }
        }

        public gameView() {}
    }
}
