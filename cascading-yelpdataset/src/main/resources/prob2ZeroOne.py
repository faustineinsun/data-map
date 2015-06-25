# @author Fei Yu (@faustineinsun)
def prob2ZeroOne(line, fwrite):
    ary = line.replace("\n","").split(",")
    if (ary[0] == "id" ):
        fwrite.write(line)
    else:
        # find the index of the max probablity catergory in ary
        maxProb = -1
        maxProbIdx = -1
        for i in range(1, 10):
            if ary[i] > maxProb:
                maxProb = ary[i]
                maxProbIdx = i

        # change probablity to zero/one
        newLine = ary[0]+","
        for i in range(1, 10):
            if i == maxProbIdx:
                newLine += "1,"
            else:
                newLine += "0,"

        # write new line to new file
        fwrite.write(newLine[:-1]+"\n")

input_file = "ml/dataset/kaggleotto/XGBoostModel_benchmark.csv"
output_file = "ml/dataset/kaggleotto/XGBoostModel_benchmark_zeroOne.csv"

fwrite = open(output_file,"w")
fread = open(input_file)

for line in fread:
    prob2ZeroOne(line, fwrite)

fread.close()
fwrite.close()
