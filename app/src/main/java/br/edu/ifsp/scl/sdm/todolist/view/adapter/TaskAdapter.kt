package br.edu.ifsp.scl.sdm.todolist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.todolist.R
import br.edu.ifsp.scl.sdm.todolist.databinding.TileTaskBinding
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task.Companion.TASK_DONE_TRUE

class TaskAdapter(
    private val taskList: List<Task>,
    private val onTaskClickListener: OnTaskClickListener
) : RecyclerView.Adapter<TaskAdapter.TaskTileViewHolder>() {
    inner class TaskTileViewHolder(tileTaskBinding: TileTaskBinding) :
        RecyclerView.ViewHolder(tileTaskBinding.root) {
        val nameTv: TextView = tileTaskBinding.nameTv
        val doneCb: CheckBox = tileTaskBinding.doneCb

        init {
            tileTaskBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onTaskClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_task,
                            menu
                        )
                        menu?.findItem(R.id.removeTaskMi)?.setOnMenuItemClickListener {
                            onTaskClickListener.onRemoveTaskMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editTaskMi)?.setOnMenuItemClickListener {
                            onTaskClickListener.onEditTaskMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onTaskClickListener.onTaskClick(adapterPosition)
                    }
                }
                doneCb.run {
                    setOnClickListener {
                        onTaskClickListener.onDoneCheckBoxClick(adapterPosition, isChecked)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileTaskBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { TaskTileViewHolder(this) }


    override fun onBindViewHolder(holder: TaskTileViewHolder, position: Int) {
        taskList[position].let { task ->
            with(holder) {
                nameTv.text = task.name
                doneCb.isChecked = task.done == TASK_DONE_TRUE
            }
        }
    }

    override fun getItemCount() = taskList.size
}