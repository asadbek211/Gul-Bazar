package com.bizmiz.gulbozor.ui.bottom_nav.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bizmiz.gulbozor.R;

import java.util.HashMap;
import java.util.List;

public class ExListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTitle;
    private HashMap<String, Contacto> expandableListData;

    public ExListAdapter(Context context,
                         List<String> listTitle,
                         HashMap<String, Contacto> expandableListData) {
        this.context = context;
        this.listTitle = listTitle;
        this.expandableListData = expandableListData;
    }

    @Override
    public int getGroupCount() {
        return this.listTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return this.listTitle.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListData.get(this.listTitle.get(groupPosition));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View converView, ViewGroup parent) {
        String mainTitle = (String) getGroup(i);
        Contacto contacto = (Contacto) getChild(i, 0);

        if (converView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView textView = converView.findViewById(R.id.category_flower);
        textView.setText(mainTitle);
        //17:50
       /* textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundResource(R.drawable.button_shape);
            }
        });*/

        return converView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        Contacto contacto = (Contacto) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView bucketCat = view.findViewById(R.id.bucket_flowers_txt);
        TextView homemadeCat = view.findViewById(R.id.homemade_flowers_cat_txt);
        TextView potCat = view.findViewById(R.id.pot_flowers_cat_txt);
        ImageView greenLine = view.findViewById(R.id.just_green);

        if (contacto.getChildRaw3() == null) {
            potCat.setVisibility(View.GONE);
        } else {
            potCat.setVisibility(View.VISIBLE);
            bucketCat.setText(contacto.getChildRaw1());
            homemadeCat.setText(contacto.getChildRaw2());
            potCat.setText(contacto.getChildRaw3());
        }
        bucketCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), bucketCat.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        potCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), potCat.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        homemadeCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), homemadeCat.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        view.setAnimation(animation);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
