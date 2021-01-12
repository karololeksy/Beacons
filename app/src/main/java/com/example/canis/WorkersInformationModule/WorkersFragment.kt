package com.example.canis.WorkersInformationModule
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.canis.R
import com.example.canis.WorkersInformationModule.model.Worker
import kotlinx.android.synthetic.main.worker_module_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WorkersFragment : Fragment() {

    private val service = InstanceProvider.getWorkerServiceInstance()

    val adapter = WorkersAdapter{onItemClicked(it)}

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.worker_module_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            val workers = service.fetchWorkers()

            workersRecycleView.layoutManager = LinearLayoutManager(context)
            adapter.addList(workers)
            workersRecycleView.adapter = adapter
        }


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if(context is OnWorkerClickedListener){
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnCourseClickedListener")
//        }
    }

    private fun onItemClicked(worker: Worker){
        val workerDetailsItemFragment = WorkerDetailsItemFragment.newInstance(worker)
        fragmentManager?.let{
            it.beginTransaction()
                    .addToBackStack("worker_item")
                    .replace(R.id.workersModule,workerDetailsItemFragment)
                    .commit()
        }
    }

    override fun onDetach() {
        super.onDetach()
        // listener = null
    }

//    interface OnWorkerClickedListener{
//        fun onWorkerClicked(id: Long)
//    }

    companion object{
        @JvmStatic
        fun newInstance() = WorkersFragment()
    }

}