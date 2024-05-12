import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.database.Task
import com.example.taskapp.database.TaskDB
import com.example.taskapp.database.TaskRepository
import com.example.taskapp.viewModel.MainActivityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlin.time.Duration
import kotlinx.coroutines.delay


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel:MainActivityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val titleEditText: EditText = view.findViewById(R.id.titleEditText)
        val dateEditText: EditText = view.findViewById(R.id.dateEditText)
        val timeEditText: EditText = view.findViewById(R.id.timeEditText)
        val descEditText:EditText=view.findViewById(R.id.desc_edittext)
        val add_btn: Button = view.findViewById(R.id.add_btn)
        val cancel_btn: Button = view.findViewById(R.id.cancel_btn)

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]
        val repository = TaskRepository(TaskDB.getInstance(requireContext()))

        dateEditText.setOnClickListener { showDatePicker() }
        timeEditText.setOnClickListener { showTimePicker() }

        add_btn.setOnClickListener {
            val title = titleEditText.text.toString()
            val date = dateEditText.text.toString()
            val time = timeEditText.text.toString()
            val status = descEditText.text.toString()
            val formData = Task(title, date, time, status)

            // Assuming 'repository' is your repository instance
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    repository.insert(formData)

                    val data = repository.getAllItems()

                    withContext(Dispatchers.Main) {
                        titleEditText.text.clear()
                        dateEditText.text.clear()
                        timeEditText.text.clear()
                        descEditText.text.clear()

                        viewModel.setData(data)
                        showAlert("Task added successfully")



                    }
                } catch (e: Exception) {
                    // Handle the exception here
                    e.printStackTrace()
                }
            }

        }

        return view
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Success")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Set the selected date to the EditText
                val dateEditText: EditText? = view?.findViewById(R.id.dateEditText)
                dateEditText?.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            }, mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Format the minute to always display two digits
                val formattedMinute = String.format("%02d", minute)
                // Set the selected time to the EditText
                val timeEditText: EditText? = view?.findViewById(R.id.timeEditText)
                timeEditText?.setText("$hourOfDay:$formattedMinute")
            },
            mHour,
            mMinute,
            false
        )
        timePickerDialog.show()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
