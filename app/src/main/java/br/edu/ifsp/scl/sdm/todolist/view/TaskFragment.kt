package br.edu.ifsp.scl.sdm.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.sdm.todolist.R
import br.edu.ifsp.scl.sdm.todolist.databinding.FragmentTaskBinding
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task.Companion.TASK_DONE_FALSE
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task.Companion.TASK_DONE_TRUE
import br.edu.ifsp.scl.sdm.todolist.view.MainFragment.Companion.EXTRA_TASK
import br.edu.ifsp.scl.sdm.todolist.view.MainFragment.Companion.TASK_FRAGMENT_REQUEST_KEY

class TaskFragment : Fragment() {
    private lateinit var ftb: FragmentTaskBinding
    private val navigationArgs: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.task_details)

        ftb = FragmentTaskBinding.inflate(inflater, container, false)

        val receivedTask = navigationArgs.task
        receivedTask?.also { task ->
            with(ftb) {
                nameEt.setText(task.name)
                doneCb.isChecked = task.done == TASK_DONE_TRUE
                navigationArgs.editTask.also { editTask ->
                    nameEt.isEnabled = editTask
                    doneCb.isEnabled = editTask
                    saveBt.visibility = if (editTask) VISIBLE else GONE
                }
            }
        }

        ftb.run {
            saveBt.setOnClickListener {
                setFragmentResult(TASK_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    putParcelable(
                        EXTRA_TASK, Task(
                            receivedTask?.time ?: System.currentTimeMillis(),
                            nameEt.text.toString(),
                            if (doneCb.isChecked) TASK_DONE_TRUE else TASK_DONE_FALSE
                        )
                    )
                })
                findNavController().navigateUp()
            }
        }

        return ftb.root
    }
}