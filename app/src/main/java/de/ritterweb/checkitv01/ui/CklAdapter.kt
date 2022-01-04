package de.ritterweb.checkitv01.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
//        private val cklLists: List<Ckl>    : hier wird die Datenquelle und deren Typ als ArrayList übergeben
//        private val listener: OnItemClickListener  : hier wird der OnItemClickListener des aufrufenden Fragements übergeben
//                                                     Dies dient dazu, den OnItemClickListener ausserhalb des Adapater im Code des Fragements setzten zu können
//                                                     Achtung: hier muss beim Tippen der OnClickListener des eigenen Projekts (de.ritterweb.checkit... und nicht der von android.widget.Adap... genutzt werden
//                                                     Siehe  Stelle 7:00 https://www.youtube.com/watch?v=wKFJsrdiGS8&list=PLrnPJCHvNZuCqEyW_LVTM9r6NnyGD4Db8&index=4
//        Der Typ ist RecyclerView.Adapter als ckladatper3.ExampleViewHolder, also der hier in dieser Classe definerten ExampelViewHolder
//
class CklAdapter(
    var cklLists: ArrayList<Ckl>,    /// diese InputVariable wird nicht private gesetzt, ist also public und kann daher von außen zugegriffeen werden umZ.B den Datensatz einer bestimmten Stelle im Adapter zuzugreifen
    private val mOnItemClicklistener: OnItemClickListener,    //( hier wird der in der aufrufenden Klasse definierte OnItemClickListener übergeben
    private val mOnItemLongClickListener: OnItemLongClickListener
) :
    RecyclerView.Adapter<CklAdapter.ExampleViewHolder>() {

    // hier wird eine statisches Array  statusDrawables von Drawables angelegt, dass anschließen beim Ausfüllen dew Icons in OnBindViewholder
    // verwendet wird um das jeweilige Icon für den Status anzuzeigen
    private val statusDrawables =
        arrayOf(R.drawable.ic_checklist, R.drawable.ic_work_in_progress, R.drawable.ic_done_all)

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
        val itemView =
            LayoutInflater.from(parent.context).inflate((R.layout.item_rv_main), parent, false)

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

        val currentItem =
            cklLists[position]   // currentItem, ist der Eintrag aus dem Dataset, der angezeigt werdensoll ( übergabeparameter position)
        holder.tvName.text = currentItem.name
        holder.tvBeschreibung.text = currentItem.beschreibung
        holder.ivIcon.setImageResource(statusDrawables[currentItem.status])     // die einfacher zu verwendende Methode 'ivIcon.imageRessource' gibt es nicht, also muss mit dem Setter "ivIcon.setImageRessource()" gearbeitet werden
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


    inner class ExampleViewHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {
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
        // der ViewHolder erbt von RecyclerView.ViewHolder, als auch von View.OnCklickListener, da wir hier die OnClickListener einbauen
        //
        // die Klasse wird als inner Class definiert, da eine inner Class auf die in der übergeordneten Klasse definierten Variablen etc. zugreifen kann
        //  gebraucht wird das, damit die OnClickListener auf die im Aufruf der Adapterklasse übergebenen Listener Zugriff hat
        // Einziger Nachteil: die ViewHolder Class wird damit abhängig von der übergeordenten Adapter Class. Kann also nicht in einem anderen Adapter oder sonst wo gebraucht werden.
        //  Kein großer Nachteil, da der ViewHolder enge mit dem Apdapter gekoppelt ist.
        //  Die Alternative wäre es die variable listener dem ViewHolder in seiner Argumentenliste zu übergeben
        //      ( Dazu aufnehmen in die Definitino und beim Aufruf des ViewHolders in der OnCreateViewHolder funktion oben)


        val tvName: TextView = itemView.tvName
        val tvBeschreibung: TextView = itemView.tvBeschreibung
        val ivIcon: ImageView = itemView.ivIcon

        // im Init Block werden die onClickListener angelegt.
        // Das passiert hier im ViewHolder, da die ClickListner dann auf die wenigen ViewHolder gesetzt werden, die in der View angzeigt werden
        // Alternativ könnte man das auch in OnBindViewHolder aufrufen, dann würde der onClickHolder aber jedesmal gesetzt, wenn ein neuer Datensatz
        // in den ViewHolder gefüllt wird. Das kostet Performance. Also : immer im ViewHolder definieren
        //
        // Achtung, der
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

        }


        // Die OnClick Methode sollte nicht im adapter definiert werden, da dieser nur die Daten managen soll.
        // stattdessen wird die onClickMethode im Fragement angelegt.
        // in diesem Adapter hier wird ein Interface definiert, das anschließend im Fragment implementiert ( mit Inhalt gefüllt) und ausgeführt wird
        override fun onClick(v: View?) {
            val position: Int =
                adapterPosition   // adapterPosition ist ein Property der Adapter Klasse und liefert die Positin des aktuell ausegwählten Datensatzes
            if (position != RecyclerView.NO_POSITION) {// If Abfrage stellt sicher, dass die Positino noch existiert nund nicht gerade eben zu. gelöscht wurde
                mOnItemClicklistener.onItemClick(position)   // Der im Fragement definierte und der Klasse im Aufruf übergebene listener des Fragements wird für OnItemClick mit der aktuellen Position ausgelöst
            }
        }

        // Die OnClick Methode sollte nicht im adapter definiert werden, da dieser nur die Daten managen soll.
        // stattdessen wird die onClickMethode im Fragement angelegt.
        // in diesem Adapter hier wird ein Interface definiert, das anschließend im Fragment implementiert ( mit Inhalt gefüllt) und ausgeführt wird
        override fun onLongClick(v: View?): Boolean {
            val position: Int =
                adapterPosition   // adapterPosition ist ein Property der Adapter Klasse und liefert die Positin des aktuell ausegwählten Datensatzes
            if (position != RecyclerView.NO_POSITION) {// If Abfrage stellt sicher, dass die Positino noch existiert nund nicht gerade eben zu. gelöscht wurde
                mOnItemLongClickListener.onItemLongClick(position)   // Der im Fragement definierte und der Klasse im Aufruf übergebene listener des Fragements wird für OnItemClick mit der aktuellen Position ausgelöst
            }
            return true
        }
    }

    fun updateContent(cklListstoupdate: ArrayList<Ckl>) {
        this.cklLists = cklListstoupdate
        notifyDataSetChanged()
    }

    // Implementierung der OnClickFunktion als Interface:
    // Interface bedeutet, dass in dieser Klasse nur der minimale Rumpf angelegt wird und die Funktion in der aufrufenden KLasse zu implementieren ist.
    // Interface muss in der aufrufenden Klasse zwingend impementert werden. Damit
    // Jede Klasse die den OnItemClickListner des Adapters implementiert,
    // muss auch die funktion onItemClick implementieren und mit eigenem Code füllen
    interface OnItemClickListener {
        fun onItemClick(postion: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(postion: Int)
    }
}