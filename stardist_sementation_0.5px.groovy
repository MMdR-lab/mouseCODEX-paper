import qupath.lib.gui.dialogs.Dialogs
def pathModel = "D:\\Transfer\\Qu path\\scripts supporting open-source tools for CODEX image analysis\\dsb2018_heavy_augment.pb" //wherever the path of your stardist model is
def pathModel_2 = pathModel.toString()

def stardist = StarDist2D.builder(pathModel_2)
        .threshold(0.7)              // Probability (detection) threshold
        .channels(0)            // Select detection channel, DAPI in this case
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection (change depending on your acquisition magnification)
        .cellExpansion(5.0)          // Approximate cells based upon nucleus expansion
        .cellConstrainScale(1.5)     // Constrain cell expansion using nucleus size
        .measureShape()              // Add shape measurements
        .measureIntensity()          // Add cell measurements (in all compartments)
        .includeProbability(true)    // Add probability as a measurement (enables later filtering)
        .build()

// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'