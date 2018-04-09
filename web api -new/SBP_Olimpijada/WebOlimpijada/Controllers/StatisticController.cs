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
    public class StatisticController : ApiController
    {
        // GET api/Statistic
        public IEnumerable<Statistic> Get()
        {
            DataProvider d = new DataProvider();

            IEnumerable<Statistic> statistics = d.ReturnStatisticsList();
            return statistics;
        }

        // GET api/Statistic/17
        public statisticView Get(int id)
        {

            DataProvider provider = new DataProvider();

            statisticView s = provider.GetStatisticView(id);

            if (s.Id_statistic != id)
            {
                throw new HttpException(404, "Item Not Found");
            }
            else
            {
                return s;
            }
        }

        // POST api/Statistic
        public int Post([FromBody]Statistic v)
        {
            DataProvider provider = new DataProvider();

            return provider.AddStatistic(v);
        }

        // PUT api/Statistic/17
        public int Put(int id, [FromBody]Statistic v)
        {
            DataProvider provider = new DataProvider();

            return provider.UpdateStatistic(v);
        }

        // DELETE api/Statistic/17
        public int Delete(int id)
        {
            DataProvider provider = new DataProvider();

            return provider.RemoveStatistic(id);
        }
    }
}