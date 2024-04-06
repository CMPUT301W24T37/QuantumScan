package com.example.quantumscan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Announcement> announcementList;
    private ListView announcementListView;
    private CommunityFragmentAdapter announcementAdapter;
    private TextView communityText;
    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        communityText = view.findViewById(R.id.notificationTextView);
        communityText.setVisibility(View.INVISIBLE);
        announcementList = new ArrayList<>();
        announcementListView = view.findViewById(R.id.announcementListView);
        announcementAdapter = new CommunityFragmentAdapter(this.getContext(), announcementList);
        announcementListView.setAdapter(announcementAdapter);

        // book list click
        FireStoreBridge fb = new FireStoreBridge("EVENT");
        String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        fb.retrieveAnnouncement(userId, new FireStoreBridge.OnRetrieveAnnouncement() {
            @Override
            public void onRetrieveAnnouncement(Announcement announcement) {
                if (announcement != null) {
                    announcementListView.setVisibility(View.VISIBLE);
                    communityText.setVisibility(View.INVISIBLE);
                    announcementList.add(announcement);
                    announcementListView.setAdapter(announcementAdapter);
                    System.out.println(announcementAdapter.getSize());
                }
                System.out.println(announcementAdapter.getSize());
                if (announcementAdapter.getSize() == 0){
                    System.out.println("is empty--------");
                    announcementListView.setVisibility(View.INVISIBLE);
                    communityText.setVisibility(View.VISIBLE);
                }
            }
        });
        announcementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                refresh();
            }
        });

        return view;
    }
    public void refresh(){
        announcementAdapter = new CommunityFragmentAdapter(this.getContext(), announcementList);
        announcementListView.setAdapter(announcementAdapter);
    }



}