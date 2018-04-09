using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using NHibernate;
using SBP_Olimpijada.Entiteti;

namespace SBP_Olimpijada
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Prikazi_kontinente_Click(object sender, EventArgs e)
        {
            
            //KontinentiSviPrikaz k = new KontinentiSviPrikaz();

            //k.Show();
        }

        private void Prikazi_zemlje_Click(object sender, EventArgs e)
        {
            //ZemljaInformacije InfoForm = new ZemljaInformacije("Evropa");

            //InfoForm.Show();
        }

        private void Prikazi_osobu_Click(object sender, EventArgs e)
        {
        //    OsobaInformacije InfoForm = new OsobaInformacije();
        //    InfoForm.Show();
        }

        private void Prikazi_sport_Click(object sender, EventArgs e)
        {
            //Sportovi d = new Sportovi();
            //d.Show();
        }

        private void Prikazi_discipline_Click(object sender, EventArgs e)
        {
            //DisciplineSvePrikaz d = new DisciplineSvePrikaz();
            //d.Show();
        }

        private void Prikazi_takmicenje_Click(object sender, EventArgs e)
        {
            //TakmicenjeInformacije InfoForm = new TakmicenjeInformacije();
            //InfoForm.Show();
        }

        private void Postignut_rekord_Click(object sender, EventArgs e)
        {
            //PostignutRekordPrikaz p = new PostignutRekordPrikaz();

            //p.Show();
        }

        private void Ucestvuje_s_Click(object sender, EventArgs e)
        {
            //UcestvujeSportistaInformacije u = new UcestvujeSportistaInformacije();
            //u.Show();
        }

        private void Ucestvuje_e_Click(object sender, EventArgs e)
        {
            //UcestvujeEkipaInformacije i = new UcestvujeEkipaInformacije();
            //i.Show();
        }

        private void Sadrzi_e_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Ekipni p = s.Load<SBP_Olimpijada.Entiteti.Ekipni>(2);
            //    string ss="Ekipnom sportu kosarka pripadaja disciplina: \n";
            //    foreach (Ekipne_discipline o in p.ekipne_d)
            //    {
            //        ss += o.Ime_discipline+"\n";
                   
            //    }
            //   MessageBox.Show(ss);
            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Sadrzi_p_Click(object sender, EventArgs e)
        {
           /* try
            {
                ISession s = DataLayer.GetSession();

                //Ucitavaju se podaci o prodavnici za zadatim brojem
                SBP_Olimpijada.Entiteti.Pojedinacni p = s.Load<SBP_Olimpijada.Entiteti.Pojedinacni>(0);

                foreach (Pojedinacne_discipline o in p.pojedinacne_d)
                {*/
                   // MessageBox.Show("Nema podataka o pojedinacnim sportovima u bazi");
        /*        }
        
                s.Close();
            }
            catch (Exception ec)
            {
                MessageBox.Show(ec.InnerException.ToString());
            }*/
        }

        private void Sadrzi_k1_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Kombinovani p = s.Load<SBP_Olimpijada.Entiteti.Kombinovani>(1);

            //    foreach (Ekipne_discipline o in p.ekipne_d)
            //    {
            //        MessageBox.Show("Kombinovanom sportu Atletika pripada ekipna disciplina: " + o.Ime_discipline);
            //    }

            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Sadrzi_k2_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Kombinovani p = s.Load<SBP_Olimpijada.Entiteti.Kombinovani>(1);

            //    foreach (Pojedinacne_discipline o in p.pojedinacne_d)
            //    {
            //        MessageBox.Show("Kombinovanom sportu Atletika pripada pojedinacna disciplina: " + o.Ime_discipline);
            //    }

            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Ekipe_jedne_zemlje_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Zemlja p = s.Load<SBP_Olimpijada.Entiteti.Zemlja>("USA");

            //    foreach (Ekipa o in p.ekipe)
            //    {
            //        MessageBox.Show("Ekipa koja igra za Sjedinjene Americke Drzave je: "+o.Naziv);
            //    }

            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Osobe_zemlja_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Zemlja p = s.Load<SBP_Olimpijada.Entiteti.Zemlja>("USA");

            //    foreach (Osoba o in p.osobe)
            //    {
            //        MessageBox.Show("Za Sjedinjene Americke Drzave na olimpiadi ucestvuje: "+o.Ime+" "+o.Prezime);
            //    }

            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Je_prvak_Click(object sender, EventArgs e)
        {
            //JePrvakPrikaz p = new JePrvakPrikaz();

            //p.Show();
        }

        private void Takmici_se_Click(object sender, EventArgs e)
        {
            //TakmiciSePrikaz p = new TakmiciSePrikaz();

            //p.Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //try
            //{
            //    ISession s = DataLayer.GetSession();

            //    //Ucitavaju se podaci o prodavnici za zadatim brojem
            //    SBP_Olimpijada.Entiteti.Ekipa p = s.Load<SBP_Olimpijada.Entiteti.Ekipa>(6);

            //    foreach (Je_deo o in p.deo)
            //    {
            //        MessageBox.Show("Osoba "+o.PripadaSportisti.Ime+" je deo ekipe iz zemlje "+o.PripadaSportisti.PripadaZemlji.Ime_zemlje);
            //    }

            //    s.Close();
            //}
            //catch (Exception ec)
            //{
            //    MessageBox.Show(ec.InnerException.ToString());
            //}
        }

        private void Sudi_Click(object sender, EventArgs e)
        {
           // SudiInformacije InfoForm = new SudiInformacije();
            //InfoForm.Show();
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
           // EkipeInformacije InfoForm = new EkipeInformacije();
           // InfoForm.Show();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
