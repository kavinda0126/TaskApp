import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.Adapter.TaskAdapter
import com.example.taskapp.R
import com.example.taskapp.database.TaskDB
import com.example.taskapp.database.TaskRepository
import com.example.taskapp.viewModel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var adapter: TaskAdapter
    private lateinit var viewModel: MainActivityData
    private lateinit var repository: TaskRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.rvTaskList)
        //val addBtn: Button = rootView.findViewById(R.id.btn_addtask)
        repository = TaskRepository(TaskDB.getInstance(requireContext()))

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]

        viewModel.data.observe(viewLifecycleOwner) { tasks ->
            adapter = TaskAdapter(tasks, repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllItems()

            withContext(Dispatchers.Main) {
                viewModel.setData(data)
            }
        }



        return rootView
    }


}
