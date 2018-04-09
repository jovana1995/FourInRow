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
        // GET api/Game
        public IEnumerable<Game> Get()
        {
            DataProvider d = new DataProvider();

            return d.ReturnGames();
        }

        // GET api/Game/44
        public gameView Get(int id)
        {

            DataProvider provider = new DataProvider();

            gameView g = provider.GetGameView(id);

            if (g.Id_game != id)
            {
                throw new HttpException(404, "Item Not Found");
            }
            else
            {
                return g;
            }
        }

        // POST api/Game
        public string Post([FromBody] Game g)
        {
            DataProvider provider = new DataProvider();

            return provider.AddGame(g);
        }

        // DELETE api/Game/44
        public int Delete(int id)
        {
            DataProvider provider = new DataProvider();

            return provider.RemoveGame(id);
        }
        public string Put(int id, [FromBody]Game v)
        {
            DataProvider provider = new DataProvider();

            return provider.UpdateGame(v);
        }
    }
}