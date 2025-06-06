package com.example.easycontacts.adapter;

import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easycontacts.R;
import com.example.easycontacts.data.ContactItem;
import com.example.easycontacts.data.LetterItem;
import com.example.easycontacts.data.ListItem;
import com.example.easycontacts.model.Contact;
import java.util.*;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListItem> items = new ArrayList<>();
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Contact contact, int position);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<ListItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_letter_header, parent, false);
            return new LetterViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            return new ContactViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem item = items.get(position);
        if (item.getType() == ListItem.TYPE_HEADER) {
            LetterItem letterItem = (LetterItem) item;
            ((LetterViewHolder) holder).tvLetter.setText(letterItem.getLetter());
        } else {
            ContactItem contactItem = (ContactItem) item;
            Contact contact = contactItem.getContact();
            ((ContactViewHolder) holder).tvName.setText(contact.name);

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(contact);
                }
            });

            holder.itemView.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(contact, holder.getAdapterPosition());
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class LetterViewHolder extends RecyclerView.ViewHolder {
        TextView tvLetter;

        public LetterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.tv_letter);
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}

