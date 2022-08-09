package com.D0st.bmicalc

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.D0st.bmicalc.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(R.layout.fragment_second) {

    private val binding by viewBinding(FragmentSecondBinding::bind)
    private val args: SecondFragmentArgs by navArgs()

    private var weight: Double = 1.0
    private var height: Double = 1.0
    private var result: Double = 0.0
    private var gender: Int = 0

    // handle permission dialog
//    private val requestLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) shareImage() else showErrorDialog()
//        }

    private fun showErrorDialog() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        weight = intent.getDoubleExtra("Weight", 50.0)
//        height = intent.getDoubleExtra("Height", 1.0)
//        gender = intent.getIntExtra("Gender", 0)
        weight = args.weight.toDouble()
        height = args.height.toDouble()
        gender = args.gender

        bmiCal()
        animationView()
        binding.reloadBtn.setOnClickListener {

//            backPreviousPage()

        }

        binding.deleteBtn.setOnClickListener {

//            backPreviousPage()

        }

        binding.shareBtn.setOnClickListener {
//            shareImage()
        }

    }

//    private fun shareImage() {
//        if (!isStoragePermissionGranted()) {
//            requestLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            return
//        }
//
//        // unHide the app logo and name
////        showAppNameAndLogo()
//        val imageURI = binding.detailView.drawToBitmap().let { bitmap ->
//            //           hideAppNameAndLogo()
//            saveBitmap(this, bitmap)
//        } ?: run {
//            displayToast("Error occurred!")
//            return
//        }
//
//        val intent = ShareCompat.IntentBuilder(this)
//            .setType("image/jpeg")
//            .setStream(imageURI)
//            .intent
//
//        startActivity(Intent.createChooser(intent, null))
//    }

//    private fun showAppNameAndLogo() = with(_binding.transactionDetails) {
//        appIconForShare.show()
//        appNameForShare.show()
//    }
//
//    private fun hideAppNameAndLogo() = with(binding.transactionDetails) {
//        appIconForShare.hide()
//        appNameForShare.hide()
//    }

    private fun isStoragePermissionGranted(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED


//    private fun shareText() = with(binding) {
//        val shareMsg = getString(
//            3,
//            ""
//            R.string.share_message,
//            transactionDetails.title.text.toString(),
//            transactionDetails.amount.text.toString(),
//            transactionDetails.type.text.toString(),
//            transactionDetails.tag.text.toString(),
//            transactionDetails.date.text.toString(),
//            transactionDetails.note.text.toString(),
//            transactionDetails.createdAt.text.toString()
//        )
//
//        val intent = ShareCompat.IntentBuilder(Activity())
//            .setType("text/plain")
//            .setText(shareMsg)
//            .intent
//
//        startActivity(Intent.createChooser(intent, null))
//    }


//    private fun backPreviousPage(){
//        animationViewUp()
//        Handler().postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//        }, 600)
//
//    }

    private fun animationView() {

        binding.apply {

            deskImage.translationY = 100f
            resultText.translationY = 40f
            bmiText.translationY = 50f
            bmiTextNormal.translationY = 50f
            deleteBtn.translationY = 70f
            reloadCardView.translationY = 70f
            shareBtn.translationY = 70f

            deskImage.alpha = 0f
            resultText.alpha = 0f
            bmiText.alpha = 0f
            bmiTextNormal.alpha = 0f
            deleteBtn.alpha = 0f
            reloadCardView.alpha = 0f
            shareBtn.alpha = 0f

            deskImage.setPadding(100)

            deskImage.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(300)
                .start()
            deskImage.setPadding(0)
            resultText.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(500)
                .start()
            bmiText.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(450).start()
            bmiTextNormal.animate().translationY(0f).alpha(.3f).setDuration(500).setStartDelay(500)
                .start()
            deleteBtn.animate().translationY(0f).alpha(.3f).setDuration(500).setStartDelay(600)
                .start()
            reloadCardView.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(750)
                .start()
            shareBtn.animate().translationY(0f).alpha(.3f).setDuration(500).setStartDelay(900)
                .start()


        }
    }

    private fun animationViewUp() {

        binding.apply {

            textView.animate().translationY(0f).alpha(0f).setDuration(500).setStartDelay(0)
                .start()
            deskImage.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(0)
                .start()

            resultText.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(50)
                .start()
            bmiText.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(100)
                .start()
            bmiTextNormal.animate().translationY(-250f).alpha(0f).setDuration(500)
                .setStartDelay(150)
                .start()
            deleteBtn.animate().translationY(-250f).alpha(0f).setDuration(300).setStartDelay(200)
                .start()
            reloadCardView.animate().translationY(-250f).alpha(0f).setDuration(300)
                .setStartDelay(250).start()
            shareBtn.animate().translationY(-250f).alpha(0f).setDuration(300).setStartDelay(300)
                .start()


        }
    }


    private fun bmiCal() {
        if (height > 0 && weight > 0) {
            if (gender == 0) {
                bmiCalMale()
            } else if (gender == 1) {
                bmiCalFemale()
            }
            showResult()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showResult() {

        val solution = String.format("%.1f", result)
        binding.resultText.text = solution
        binding.bmiText.apply {
            if (result < 18.5) {
                this.text = "You are Under Weight"
            } else if (result >= 18.5 && result < 24.9) {
                this.text = "You are Healthy"
            } else if (result >= 24.9 && result < 30) {
                this.text = "You are Overweight"
            } else if (result >= 30) {
                this.text = "You are Suffering from Obesity"
            }
        }


    }

    private fun bmiCalMale() {
        result = ((weight / (height * height)) * 10000)
    }

    private fun bmiCalFemale() {
        result = ((weight / (height * height)) * 10000)
    }

//    override fun onBackPressed() {
//        backPreviousPage()
//    }

}

