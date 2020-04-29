package com.example.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private int size;

    public MyAdapter(int size) {
        this.size = size;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int idForItem = R.layout.recycler_view_text;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(idForItem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    private static final String units[][] = {{"один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"}, {"одна", "две"}};
    private static final String dozs[] = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private static final String dozens[] = {"двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String hundreds[] = {"сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};
    private static final String thousand_million[][] = { {"", "", "", "1"}, {"тысяча", "тысячи", "тысяч", "0"}, {"миллион", "миллиона", "миллионов", "1"}};

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        void bind(int position) {
            textView.setBackgroundResource(R.drawable.background_one);
            if (position % 2 == 1)
                textView.setBackgroundResource(R.drawable.background_two);
            String result = word_function(position, 0);
            textView.setText(result);
        }


        String word_function(long num, int discharge) {
            String word = "";
            int genus = thousand_million[discharge][3].indexOf("0") + 1;
            int h = (int) (num % 1000);
            int hundred = h / 100;              //сотни
            if (hundred > 0) word = (hundreds[hundred - 1]) + " ";
            int unit = h % 100;
            int dozen = unit / 10;              //десятки
            unit = unit % 10;                   //единицы
            switch (dozen) {
                case 0:
                    break;
                case 1:
                    word = word + dozs[unit] + " ";
                    unit = 0;
                    break;
                default:
                    word = word + (dozens[dozen - 2]) + " ";
            }

            switch (unit) {
                case 0:
                    break;
                case 1:
                case 2:
                    word = word + units[genus][unit - 1] + " ";
                    break;
                default:
                    word = word + units[0][unit - 1] + " ";
            }
            switch (unit) {
                case 1:
                    word = word + thousand_million[discharge][0];
                    break;
                case 2:
                case 3:
                case 4:
                    word = word + thousand_million[discharge][1];
                    break;
                default: if((h != 0))
                    word = word + thousand_million[discharge][2];
            }
            long next_num = num / 1000;
            if (next_num > 0) {
                return (word_function(next_num, discharge + 1) + " " + word).trim();
            } else {
                textView.setText(word);
                return word.trim();
            }
        }
    }
}