package br.edu.ifsp.scl.sdm.todolist.view.adapter

interface OnTaskClickListener {
    fun onTaskClick(position: Int)
    fun onRemoveTaskMenuItemClick(position: Int)
    fun onEditTaskMenuItemClick(position: Int)
    fun onDoneCheckBoxClick(position: Int, checked: Boolean)
}