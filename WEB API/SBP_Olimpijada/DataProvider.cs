using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SBP_Olimpijada.Entiteti;
using NHibernate;
using NHibernate.Linq;
using SBP_Olimpijada.DTOs;

namespace SBP_Olimpijada
{
    public class DataProvider
    {
        public IEnumerable<Game> ReturnGames()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Game> games = s.Query<Game>()
                                                .Select(p => p);

            return games;
        }

        public Game GetGame(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Game>()
            .Where(v => v.Id_game == id).Select(p => p).FirstOrDefault();
        }

        public gameView GetGameView(int id)
        {
            ISession s = DataLayer.GetSession();

            Game voj = s.Query<Game>()
                .Where(v => v.Id_game == id).Select(p => p).FirstOrDefault();

            if (voj == null) return new gameView();

            return new gameView(voj);
        }

        public int AddGame(Game g)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                s.Save(g);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception ec)
            {
                return -1;
            }
        }

        public int RemoveGame(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Game g = s.Load<Game>(id);

                s.Delete(g);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception exc)
            {
                return -1;
            }

        }

        public IEnumerable<Statistic> ReturnStatisticsList()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Statistic> statistics = s.Query<Statistic>()
                                                .Select(p => p);

            return statistics;
        }

        public Statistic GetStatistic(int odId)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Statistic>()
            .Where(v => v.Id_statistic == odId).Select(p => p).FirstOrDefault();
        }

        public statisticView GetStatisticView(int barkod)
        {
            ISession s = DataLayer.GetSession();

            Statistic voj = s.Query<Statistic>()
                .Where(v => v.Id_statistic == barkod).Select(p => p).FirstOrDefault();

            if (voj == null) return new statisticView();
            return new statisticView(voj);
        }

        public int AddStatistic(Statistic v)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                s.Save(v);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception ec)
            {
                return -1;
            }
        }

        public int RemoveStatistic(int ime)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Statistic v = s.Load<Statistic>(ime);

                s.Delete(v);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception exc)
            {
                return -1;
            }

        }

        public int UpdateStatistic(Statistic t)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                s.Update(t);
                s.Flush();
                s.Close();

                return 1;
            }

            catch (Exception ec)
            {

                return -1;
            }

        }

        public IEnumerable<Player> ReturnPlayersList()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Player> osobe = s.Query<Player>()
                                                .Select(p => p);

            return osobe;
        }

        public Player GetPlayer(int odId)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Player>()
            .Where(v => v.Id_player == odId).Select(p => p).FirstOrDefault();
        }

        public playerView GetPlayerView(int id)
        {
            ISession s = DataLayer.GetSession();

            Player voj = s.Query<Player>()
                .Where(v => v.Id_player == id).Select(p => p).FirstOrDefault();

            if (voj == null) return new playerView();
            return new playerView(voj);
        }

        public int AddPlayer(Player v)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                s.Save(v);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception ec)
            {
                return -1;
            }
        }

        public int RemovePlayer(int ime)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Player v = s.Load<Player>(ime);

                s.Delete(v);

                s.Flush();
                s.Close();

                return 1;
            }
            catch (Exception exc)
            {
                return -1;
            }

        }

        public int UpdatePlayer(Player t)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                s.Update(t);
                s.Flush();
                s.Close();

                return 1;
            }

            catch (Exception ec)
            {

                return -1;
            }
        }
    }
}

