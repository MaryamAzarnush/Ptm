package com.azarnush.webeskan.Adapter.ResidentPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.ResidentPanel.BoardInfo;

import java.util.List;

public class BoardInfos_adapter extends RecyclerView.Adapter<BoardInfos_adapter.BoardInfosViewHolder> {
    List<BoardInfo> boardInfos;

    public BoardInfos_adapter(List<BoardInfo> boardInfos) {
        this.boardInfos = boardInfos;
    }

    @NonNull
    @Override
    public BoardInfosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_boardinfos, parent, false);
        return new BoardInfosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardInfosViewHolder holder, int position) {
        BoardInfo boardInfo = boardInfos.get(position);
        holder.board_number.setText(String.valueOf(boardInfo.getBoardId()));
        holder.board_title.setText(boardInfo.getBoardTitle());
        holder.operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return boardInfos.size();
    }

    public class BoardInfosViewHolder extends RecyclerView.ViewHolder {
        TextView board_number, board_title;
        Button operation;

        public BoardInfosViewHolder(@NonNull View itemView) {
            super(itemView);

            board_number = itemView.findViewById(R.id.board_number);
            board_title = itemView.findViewById(R.id.board_title);
            operation = itemView.findViewById(R.id.operation);
        }
    }
}
