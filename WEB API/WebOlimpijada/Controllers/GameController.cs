using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using SBP_Olimpijada.Entiteti;
using SBP_Olimpijada;
using System.Web.Http;
using SBP_Olimpijada.DTOs;

namespace WebOlimpijada.Controllers
{
    public class GameController : ApiController
    {
        // GET api/Zemlja
        public IEnumerable<Game> Get()
        {
            DataProvider d = new DataProvider();

            IEnumerable<Game> games = d.ReturnGames();
            return games;
        }

        // GET api/Zemlja/SRB
        public gameView Get(int id)
        {

            DataProvider provider = new DataProvider();

            return provider.GetGameView(id);
        }

        // POST api/Zemlja
        public int Post([FromBody] Game g)
        {
            DataProvider provider = new DataProvider();

            return provider.AddGame(g);
        }

        // DELETE api/Zemlja/SRB
        public int Delete(int id)
        {
            DataProvider provider = new DataProvider();

            return provider.RemoveGame(id);
        }
    }
}