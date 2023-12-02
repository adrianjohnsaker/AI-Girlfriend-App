package com.example.myaisweetheart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MessageAdapter(private val mMessages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = if (viewType == VIEW_TYPE_USER) {
            inflater.inflate(R.layout.item_message_user, parent, false)
        } else {
            inflater.inflate(R.layout.item_message_bot, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = mMessages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = mMessages[position]
        return if (message.isSentByUser()) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var mTextView: TextView

        fun bind(message: Message) {
            if (message.isSentByUser()) {
                mTextView = itemView.findViewById(R.id.text_message_user)
                mTextView.text = message.getText()
            } else {
                mTextView = itemView.findViewById(R.id.text_message_bot)
                mTextView.text = message.getText()
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_USER = 0
        private const val VIEW_TYPE_BOT = 1
    }
}
