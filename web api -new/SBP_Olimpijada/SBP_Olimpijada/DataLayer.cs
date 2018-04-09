using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NHibernate;
using FluentNHibernate.Cfg;
using FluentNHibernate.Cfg.Db;
using SBP_Olimpijada.Mapiranja;

namespace SBP_Olimpijada
{
    class DataLayer
    {
        private static ISessionFactory _factory = null;
        private static object objLock = new object();


        //funkcija na zahtev otvara sesiju
        public static ISession GetSession()  //zakljucali smo kriticnu sekciju da bi mogli da koristimo i multithread
        {
            //ukoliko session factory nije kreiran
            if (_factory == null)                         //singlton je jedinstven
            {
                lock (objLock)
                {
                    if (_factory == null)
                        _factory = CreateSessionFactory();
                }
            }

            return _factory.OpenSession();
        }

        //konfiguracija i kreiranje session factory
        private static ISessionFactory CreateSessionFactory()
        {
            try
            {
                var cfg = OracleManagedDataClientConfiguration.Oracle10
                .ConnectionString(c =>
                    c.Is("DATA SOURCE=gislab-oracle.elfak.ni.ac.rs:1521/SBP_PDB;PERSIST SECURITY INFO=True;USER ID=S15876;Password=S15876"));

                return Fluently.Configure()
                    .Database(cfg
                    .ShowSql())                          //da bismo videli gde su nam greske
                    .Mappings(m => m.FluentMappings.AddFromAssemblyOf<GameMapiranje>()) //pronadje klasu rodavnicamapiranja i njen asembley ucita fajl gde je mapiranje i ukljuci sve klase izvedene iz klase classmap
                    //.ExposeConfiguration(BuildSchema)
                    .BuildSessionFactory();
            }
            catch (Exception ec)
            {
                System.Windows.Forms.MessageBox.Show(ec.Message);
                return null;
            }

        }
    }
}

