package com.example.canis.WorkersInformationModule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.canis.NavigationModule.MapboxActivity
import com.example.canis.NavigationWorkersModule.MapboxWorkers
import com.example.canis.R
import com.example.canis.WorkersInformationModule.model.Worker
import kotlinx.android.synthetic.main.worker_details.*
import kotlinx.android.synthetic.main.worker_details.view.*

private const val WORKER_ID = "worker_id"

class WorkerDetailsItemFragment(private val worker: Worker) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.worker_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view).load(worker.image).placeholder(R.drawable.photo_placeholder).into(view.workerPhoto)
        firstName.text = worker.firstName
        lastName.text = worker.lastName
        contact.text = worker.contact
        address.text = worker.address
        hours.text = worker.hours

        navigationWorkerButton.setOnClickListener {
            val navigationIntent = Intent(this.context, MapboxWorkers::class.java)

            val cordsBundle = Bundle()
            cordsBundle.putDouble("latWorker",worker.lat)
            cordsBundle.putDouble("lonWorker",worker.lon)
            cordsBundle.putString("workerPlace", worker.address)
            cordsBundle.putBoolean("isWorker",true)

            navigationIntent.putExtras(cordsBundle)

            startActivity(navigationIntent)
        }
    }

    companion object{
        @JvmStatic
        fun newInstance(worker: Worker): WorkerDetailsItemFragment{
            return WorkerDetailsItemFragment(worker)
        }
    }

}