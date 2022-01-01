package de.ritterweb.checkitv01.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.ritterweb.checkitv01.R
import de.ritterweb.checkitv01.repository.database.Ckl
import kotlinx.android.synthetic.main.item_rv_main.view.*

/////////////////////////////////////////////////////////////////////////////
//  Die Adapterklasse stellt einen Adapter bereit, mit dem Daten in die itemViews der Zeilen einer Recyclerview eingefüllt werden
//  und der auf bestimmte Ereignisse reagiert.
//
//  Die Reaktion auf Click Events wird hier ebenfalls vorbereitet, allerdings werden hier nur Interfaces bereitgestellt, die im Fragment oder der Activitiy überschrieben werden
//  ( override). Damit ist der Adapter universell nutzbar, die Logik, z.B. die Reaktion auf Klicks auf ein View Item wird die die aufrufende UI Klasse verlagert
//
//  Aufruf
//        private val cklLists: List<Ckl>    : hier wird die Datenquelle und deren Typ übergeben
//        Der Typ ist RecyclerView.Adapter als ckladatper3.ExampleViewHolder, also der hier in dieser Classe definerten ExampelViewHolder
//
class CklAdapter3(private var cklLists: ArrayList<Ckl>):RecyclerView.Adapter<CklAdapter3.ExampleViewHolder> (){

    // hier wird eine statisches Array  statusDrawables von Drawables angelegt, dass anschließen beim Ausfüllen dew Icons in OnBindViewholder
    // verwendet wird um das jeweilige Icon für den Status anzuzeigen
    private val statusDrawables = arrayOf(R.drawable.ic_checklist,R.drawable.ic_work_in_progress,R.drawable.ic_done_all)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        /////////////////////////////////////////////////////////////////////////////
        //
        // OnCreateViewHolder lägt für jede Zeile in der Recyclerview eine View an.
        //
        // 'itemView' ist eine interne Variable, die hierzu angelegt wird. Sie wird zum Schluss per return zurückgegeben
        // Die View itemView wird über den LayoutInflate angelegt:
        // Syntax ist immer gleich, dennoch hier kurz zur Erläuterung:
        //      .from(parent.context)  : Bezug auf den Kontext des Parents. Parent ist die Recyclerview, deren Context ist das Fragment in dem sie stehckt
        //      .inflate (....)   Das XML File, in dem die Viewelemente definiert sind.
        //      .parent   Das ist die Recyclerview in der die View als Zeile angelegt wird.
        //      false ; attachToRoot  gibt an, das die View nicht direkt angelegt wird
        //  Diese Syntax ist grundsätzlich immer gleich: val itemView = LayoutInflater.from(parent.context).inflate((R.layout.xxxxxxx),parent,false))
        //  Lediglich die xml Source ist spezifisch
        //
        //  Erläuerung der Aufrufparameter: onCreateViewHolder(parent: ViewGroup, viewType: Int)
        //      parent: das ist die Recyclerview in der der Viewholder aufgerufen wird
        //      viewType:  das wird nur vewendet, wenn innerhalb der RecyclerView unterschiedliche ItemViews aufgerufen werden sollen
        //
        //  onCreateViewHolder wird nur ein paar mal aufgerufen. Nämlich für die itemviews, die in de RecyclerView angezeigt werden
        //  ( also zum Beispiel 10 Stück, je nach Größe) sowie ein paar drüber unter drunter für das flüsseige Scrollen
        //  entsprechend wenig Rechenleistung wird dafür benötit
        // im Gegensatz dazu  wird onBindViewHolder für alle Datensätze angelegt die angezeigt werden.
        // Das bedeutet, das die Methode bei jedem Scrollen für jedes neu angezeigte Element aufgerufen wird.
        //
        // Daher stammt auch der Name RecyclerView. Eine itemView, die durch Scrollen verschwindet wird auf de anderen Seite wieder
        // "recycelt" alsi mit anderen Daten befüllt und erneut angezeigt
        //
        ///////////////////////////////////////////////////////////////////////////////
        val itemView = LayoutInflater.from(parent.context).inflate((R.layout.item_rv_main),parent,false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {

        /////////////////////////////////////////////////////////////////////////////
        //   OnBindViewHolder füllt den Holder ( das ist die ItemView) mit den Date
        //   das passiert beim ersten Aufbau und beim Scrollen für jeden Eintrag, Also viel Rechenleistung
        //
        //   Aufrufparameter:onBindViewHolder(holder: ExampleViewHolder, position: Int)
        //      holder: das ist der konkrete ViewHolder der gefüllt werden soll   ( hier der in der eigenen Sub-Klasse definerte ExampleViewHolder
        //      position: das ist die Position im Dataset der genutzt werden soll
        //
        //
        /////////////////////////////////////////////////////////////////////////////

        val currentItem = cklLists[position]   // currentItem, ist der Eintrag aus dem Dataset, der angezeigt werdensoll ( übergabeparameter position)
        holder.tvName.text = currentItem.name
        holder.tvBeschreibung.text = currentItem.beschreibung
        holder.ivIcon.setImageResource(statusDrawables [currentItem.status])     // die einfacher zu verwendende Methode 'ivIcon.imageRessource' gibt es nicht, also muss mit dem Setter "ivIcon.setImageRessource()" gearbeitet werden
                                                                                // Das Icon wird aus dem Status abgeleitet. Hierzu wird die im Array drawables in der Klasse statisch defniierten Layouts überwiesen

        /// hier wird mit den im ViewHolder angelegten vals gearbeitet   ( "gecasht"), da ansonsten bei jedem Aufruf für einen Datensatz beimScrollen die
        /// FindbyId Funktion direkt oder indirekt auferufen werden müsste, was sehr langsam ist.

    }

    override fun getItemCount() = cklLists.size
    //   Das ist als Funktion ein Einzeiler und nochmal  einfacher zu schreiben als
    //      override fun getItemCount(): Int{
    //          return ckllist.size
    //      }
    // macht aber genau das gleiche





    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //  ViewHolder  gibt es soviele wie gerade auf dem Screen angezeigt werden.
        //  Sie werden beim Aufbau des REcyclerViews angelegt.
        //  Alles was hier gemacht wird, ist für den jeweiligen View Holder ( Das sind nur einige)
        //  die TextViews des itemViews als variablen einmalig anzulegen und ihnen die IDs der
        //  der TextViews aus dem item.View Layoutfile zuzuordnen
        //
        // Die Definition der TextViews etc. als 'val'  führt zu einer statischen Zuordnung
        // Sie können danach keinem anderen textView mehr zugewiesen werden,
        // allerdings können die Werte des Textviews-Objektes später geändert werden
        // Dies geschiet jedesmall wenn in der der itemViews beimScrollen neue Datensätze
        // in einen der ViewHolder geladen werden.

        val tvName: TextView = itemView.tvName
        val tvBeschreibung: TextView = itemView.tvBeschreibung
        val ivIcon: ImageView = itemView.ivIcon


    }

    fun updateContent(cklListstoupdate:ArrayList<Ckl>)
    {
        cklLists = cklListstoupdate
        notifyDataSetChanged()
    }
}