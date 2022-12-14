package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.adapter.library.PlaylistAdapter
import com.example.musicplayer.databinding.FragmentPlaylistBinding
import com.example.musicplayer.extension.onQueryTextChanged
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SearchViewModel


class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    companion object {
        var playlists = arrayListOf<Playlist>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //
        requireActivity().title = "test"
        val adapter = PlaylistAdapter()
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(Contanst.TAG, position.toString())
                playlistViewModel.setSelectedPlaylist(playlists[position])
                findNavController().navigate(
                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistSongFragment(
                        "Playlist: ${playlists[position].name}"
                    )
                )
            }

        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                Log.d(Contanst.TAG, position.toString())
                showMenuPopup(view, playlists[position])
            }

        })
        playlistViewModel.playlists.observe(viewLifecycleOwner) {
            Log.d(Contanst.TAG, "playlist: ${it.toString()}")
            playlists.clear()
            playlists.addAll(it)
            adapter.submitData(it)
        }
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.search_add_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView: androidx.appcompat.widget.SearchView? =
                    searchItem.actionView as androidx.appcompat.widget.SearchView?
                searchView?.onQueryTextChanged {
                    searchViewModel.searchQuery.value = it
                    searchViewModel.playlists.observe(parentFragment!!.viewLifecycleOwner) {
                        val songs = arrayListOf<Playlist>()
                        songs.addAll(it)
                        adapter.submitData(songs)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add -> {
                        Log.d(Contanst.TAG, "add")
                        CustomDialog(requireContext()).createInputDialog(object :
                            CustomDialog.OnSubmitBtnClick {
                            override fun onClick(name: String) {
                                Log.d(Contanst.TAG, "name playlist: $name")
                                playlistViewModel.insertPlaylist(name)
                            }
                        })
                        true
                    }

                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showMenuPopup(v: View, playlist: Playlist) {
        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.item_playlist_menu, menu)
            show()
            setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.edit -> {
                            CustomDialog(requireContext()).createEditDialog(playlist.name,
                                object : CustomDialog.OnSubmitBtnClick {
                                    override fun onClick(name: String) {
                                        playlistViewModel.updatePlaylist(
                                            name,
                                            playlist.idPlaylist!!
                                        )
                                    }

                                })
                        }
                        R.id.delete -> {
                            CustomDialog(requireContext()).createConfirmDialog(object :
                                CustomDialog.OnSubmitBtnClick {
                                override fun onClick(name: String) {
                                    playlistViewModel.deletePlaylist(playlist.idPlaylist!!)
                                }
                            })
                        }
                    }
                    return true
                }

            })
        }
    }
}