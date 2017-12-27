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
    public class PlayerController : ApiController
    {
        // GET api/Player
        public IEnumerable<Player> Get()
        {
            DataProvider d = new DataProvider();

            IEnumerable<Player> osobe = d.ReturnPlayersList();
            return osobe;
        }

        // GET api/Player/5
        public playerView Get(int id)
        {

            DataProvider provider = new DataProvider();

            return provider.GetPlayerView(id);
        }

        // POST api/Player
        public int Post([FromBody]Player v)
        {
            DataProvider provider = new DataProvider();

            return provider.AddPlayer(v);
        }

        // PUT api/Player/5
        public int Put(int id, [FromBody]Player v)
        {
            DataProvider provider = new DataProvider();

            return provider.UpdatePlayer(v);
        }

        // DELETE api/Player/5
        public int Delete(int id)
        {
            DataProvider provider = new DataProvider();

            return provider.RemovePlayer(id);
        }
    }
}