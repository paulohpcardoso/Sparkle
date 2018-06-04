package com.example.danijela.sparkle.model;

public enum Result {
    Fail,
    Success,
    Skip,
    Unknown;

    public static Result parse(int i) {

        switch (i)
        {
            case 0:
                return Result.Fail;
            case  1:
                return  Result.Success;
            case 2:
                return  Result.Skip;
            default:
                return  Result.Unknown;
        }
    }
}
